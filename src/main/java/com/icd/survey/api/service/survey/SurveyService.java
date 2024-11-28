package com.icd.survey.api.service.survey;

import com.icd.survey.api.dto.survey.request.CreateSurveyRequest;
import com.icd.survey.api.dto.survey.request.ItemOptionRequest;
import com.icd.survey.api.dto.survey.request.SurveyItemRequest;
import com.icd.survey.api.dto.survey.request.UpdateSurveyUpdateRequest;
import com.icd.survey.api.entity.dto.ItemResponseOptionDto;
import com.icd.survey.api.entity.dto.SurveyDto;
import com.icd.survey.api.entity.dto.SurveyItemDto;
import com.icd.survey.api.entity.survey.ItemResponseOption;
import com.icd.survey.api.entity.survey.Survey;
import com.icd.survey.api.entity.survey.SurveyItem;
import com.icd.survey.api.repository.survey.ResponseOptionRepository;
import com.icd.survey.api.repository.survey.SurveyItemRepository;
import com.icd.survey.api.repository.survey.SurveyRepository;
import com.icd.survey.common.CommonUtils;
import com.icd.survey.exception.ApiException;
import com.icd.survey.exception.response.emums.ExceptionResponseType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final SurveyItemRepository surveyItemRepository;
    private final ResponseOptionRepository responseOptionRepository;

    public void createSurvey(CreateSurveyRequest request) {

        request.setIpAddress(CommonUtils.getRequestIp());

        /* ip 당 설문조사 이름 체크 */
        Survey existingSurvey = surveyRepository.findBySurveyNameAndIpAddressAndIsDeletedFalse(request.getSurveyName(), request.getIpAddress())
                .orElse(null);

        if (existingSurvey != null) {
            throw new ApiException(ExceptionResponseType.ALREADY_EXISTS, "ip 당 설문 조사 이름은 중복될 수 없습니다.");
        }

        /* 설문 조사 엔티티 save */
        Survey survey = surveyRepository.save(Survey.createSurveyRequest(request.createSurveyDtoRequest()));

        /* 설문 조사 항목 save */
        for (SurveyItemRequest itemRequest : request.getSurveyItemList()) {

            itemRequest.validationCheck();

            SurveyItemDto itemDto = itemRequest.createSurveyItemDtoRequest();
            SurveyItem surveyItem = SurveyItem.createSurveyItemRequest(itemDto);
            surveyItem.surveyKeySet(survey.getSurveySeq());
            surveyItem = surveyItemRepository.save(surveyItem);

            /* 선택형 문항의 경우 option list 처리 */
            if (Boolean.TRUE.equals(itemRequest.isChoiceType())) {

                /* 선택형 문항의 선택 옵션 save */
                for (ItemOptionRequest optionRequest : itemRequest.getOptionList()) {
                    optionRequest.validationCheck();

                    ItemResponseOptionDto optionDto = optionRequest.createItemREsponseOptionDto();
                    ItemResponseOption option = ItemResponseOption.createItemResponseOptionRequest(optionDto);
                    option.itemKeySet(surveyItem.getItemSeq());
                    responseOptionRepository.save(option);
                }
            }
        }
    }

    public void updateSurvey(UpdateSurveyUpdateRequest request) {

        /* 엔티티 확인 */
        Survey survey = surveyRepository.findById(request.getSurveySeq()).orElse(null);

        surveyValidCheck(survey);

        survey.update(request.createSurveyDtoRequest());

        /* 기존의 설문조사 항목들 모두 disable 처리. */
        Optional<List<SurveyItem>> optionalSurveyItemList = surveyItemRepository.findAllBySurveySeq(survey.getSurveySeq());

        if (Boolean.TRUE.equals(optionalSurveyItemList.isPresent())) {
            List<SurveyItem> surveyItemList = optionalSurveyItemList.get();
            surveyItemList.forEach(SurveyItem::disable);
        }

        /* 새로운 설문조사 항목들 save */
        for (SurveyItemRequest itemRequest : request.getSurveyItemList()) {
            itemRequest.validationCheck();

            SurveyItem newItem = SurveyItem.createSurveyItemRequest(itemRequest.createSurveyItemDtoRequest());
            newItem.surveyKeySet(survey.getSurveySeq());

            newItem = surveyItemRepository.save(newItem);


            /* 민준
             * 음.. disables 된 데이터들이 '설문 조사 에 데이터' 이긴한데 이미 바뀐 데이터는 '바뀐 설문 조사 데이터'가 맞지않나 싶네요
             * 로그성이다보니 데이터가 몇개가 쌓일지도 모르고 금방금방 쌓일 수도 있는데 한 테이블에 데이터 양이 많으면 많을수록 조회나 update하는데 속도는
             * 점점 느려질 것 같습니다..
             * 핸들링이요? 테이블 하나로 관리하는건 비효율적이긴하죠
             * 아아
             * 어지간하면 안쓰는게 좋긴하죠..해도 stream
             * 자바의 똑똑한 천재들이 알고리즘 묻혀서 만든 for문이라 효율 좋긴합니다
             * 예전에는 그냥 for가 더 좋아는데 jvm도 똑똒해져서 거의 차이 안난다고는해요
             * 네넼ㅋㅋ
             * if (Boolean.TRUE.equals(optionalSurveyItemList.isPresent())) {
             * 이 부분
             * if (optionalSurveyItemList.isPresent()) {
             * 이렇게 하셔도 될겁니다 잉?
             * !? is 붙이는게 값이 있으면 true 없으면 false로 보시면 될텐데 그.....이미 충분히 명시적이라..소나린트에서 어케 경고를 주나요?
             * ㅋ엌ㅋㅋㅋ엌
             * if (Boolean.TRUE.equals(itemRequest.isChoiceType())) {
             * 이것도..
             * if (itemRequest.isChoiceType()) {
             * 이렇게..
             * 네네
             * 그으리고 이건 음 구조?긴한데
             * solid 원칙 4개는 위배된..읍읍!!
             * 크흠..
             * 지금 한 메소드에 너무 많은걸 해주는거같아요..조회도 하고~ 업데이트도 하고 인서트도하고 리턴까지..만능 객체입니다..
             * 만능 객체 세상은..금방 망한다고 책에서 본적이있습니다.. 네네 다녀오시죠
             *
             * */


            /*
             * 과감하게 말해주셔도 괜찮습니다
             *
             * 이거
             * 질문이있는데
             * 저 회의좀 다녀와서
             * */
            /*
             * 이거 보니 이건 안주네요.
             *
             * ㅇㅋ 이거는 쿨하게 빼겠습니다
             * 이게 Boolean.TRUE.equals 가 들어가면
             * 어찌됐건 동작이 추가로 수행되는 개념이니까
             * 그렇겠네요
             *
             * ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ
             * 그 어떤
             * */
            /*
             * 혹시 다른 부분은... 그냥 눈에 거슬리거나, 왜 이렇게 했는지 이해 안되거나
             * ..?
             * 그거 소나린트에서 계속 걸리더라구요
             * 정확히 저 케이스는 모르겠는데
             * 그 뭐랄까
             *
             * "test".equals("test") 이런거 if 문에 넣으면
             * 명시적으로 작성하는게 좋다 라고 메세지 나오더라구요ㅕ
             * 그래서 생각날때마다 왠만하면 넣어줍니다.
             * 이게 true 를 기대하는 조건인지 뭐 이런것도 한눈에 볼 수 있겠다 싶어서요
             *
             * */
            /*
             * 흠...
             * 알겠습니다 요것도 고민해봐야겠네요
             * */
            /*
             * 이거는 저도 다시 한번 고민 해 보겠습니다.
             *
             * 혹시 데이터 핸들링에 대해서 비효율적이다 생각하는건 어떠세요?
             * 아 그거 말구
             *
             * 이중for 이거요! ㅋㅋㅋ
             * stream 이 효율이 더 잘나오나요?
             * */


            /* 민준
             * 새로운 설문조사 항목들 save는 겹치고있지않아요??
             * createSurvey 안에서도 save를 해주고 update에서도 save를 해주고있어서요..
             * 어..재사용도 재사용인데 save가 필요한가요?? 요구사항이 아직 이해가 잘 안돼서
             * 네네ㅔㅔㅔ
             *
             * 어.....삭제되거나 변경된 항목들에 대한 응답은 쿼리로 짜면 가져올수 있을텐데..신규 설문 조사 insert가 필요한가요??
             * 아~ 그래서 그거 insert하고 변경된건 update하구요? 아~
             * 삭제되거나 수정되면 해당 로우가 없어지거나 바뀌니까 남길려고 하신거죠? That's right
             * 아~ 히든 퀘스트 ㄷㄷ 음 그럼 로그성 테이블도 하나 있는게 편하지않을까요
             * 네네
             * 보기좋다..?ㅋㅋㅋㅋㅋ 지금 상태로 하시면 로그성 + 최신 데이터 = 테이블 하나로 처리 이거잖아요??
             * 그럼 조회 하실때 기존 데이터 말고 최신 데이터만 가져오고싶으면 limit 1 해서 가져오나요?
             * 네네
             * */

            /*
             * 매니저님 저 잠깐 볼 게 있어서
             * 이 주석들 냄겨두고 저 위에 새로 달아두시면
             * 와서 보겠습니댜.
             * */
            /*
             * 그쵸
             *
             * 흠....
             * 근데 제가 체감하기로는
             * 수정되면서 disabled 된 데이터들이
             * 로그성 데이터로 분류되기에는 무리가 있지 않나 싶네요.
             * 그것도 엄연히 '설문 조사 에 데이터' 라고 저는 판단을 했습니다. (데이터의 가치가 충분하다)
             *
             * limit 1 이 왜 나온지 잘 이해가 안되는데,
             * 제 생각에
             * 그 최종 '설문조사결고조회' 에서는 isDisabled 조건을 안걸면 되고,
             * '현재 등록된 설문 조사 결과 조회' 에서는 조건을 걸면 될 것 같은데요?!
             * */
            /*
             * 차라리
             * 항목을 새로 save 하지 말고
             * update 를 하되,
             * 이 전에 항목들을 히스토리성으로 저장하는 테이블로 관리하는게 어떻냐?!
             *
             * 그 방법도 생각을 했었는데
             *
             * 저는 이게 효율적이지 않을까 생각해서 이렇게 구성해봤습니다.
             *
             * 그 방법에도 장점이 있을까요?
             * */

            /* 인성
             * 응답들은 가져올 수 있는데
             * '변경되기 전' 의 항목 데이터도 남겨두고 싶어서요
             * 네네
             *
             * 요구사항이 매니저님이 이해하셨던 대로인지,
             * 제가 생각하는 대로인지는 잘 모르겠으나
             *
             * 항목이 변경되도 기존의 응답은 조회가 가능해야한다.              -> 이 항목이 readme 에 기재되어있지는 않은데요, 당일날 구두로 전달받은 내용입니다.

             * 라는 말에서 저는 응답 뿐 아니라 항목들도 저장되어야 할 것 같아서요
             * */
            /* 인성
             * 어디가 말씀이시져?!
             * save 가 겹친다는게...?
             * 아~~~
             * 재사용을 하면 어떻겠냐는 생각이신거죠?
             * 아 무슨말 하시는지 이해 했습니다.
             *
             * 그러니까 설문조사 수정이면. 기존에 있던 항목들에 엔티티 레이어에서의 업데이트만 하면 되는데,
             * 굳이 repository.save 를 해야하냐?!
             *
             * 이게 설문조사 조회 api(아직 안만듬)에서
             * 삭제되거나 변경된 항목들에 대한 응답도 필요해서 그렇습니다.
             *
             * ex) 설문조사 응답 조회 api
             * 설문조사 : 윈큐브는 좋은 회사인가
             * 항목 :[
             * {"그런가?", "isDisabled" : true},
             * {"다른 회사는 좋을것 같냐?", "isDisabled" : "false"}
             * ]
             *
             * 이해 되시나요?!
             *
             * */


            //
            // 이 메서드가 '설문조사 업데이트' 거든요
            // 아아 update 하고 세이브도 해요? 원하시는게 둘다 하는건가요?

            // survey 자체는 무조건 update 만 하게 됩니다.
            // surveyItem 이랑 이에 해당되는 option 들은 새로 등록하는거죠
            // surveyItem과 option 은 update 되는 부분이 isDisabled 뿐입니다

            /*
             * 근데 요구사항중에,
             * 기존에 진행했던 설문은 모두 응답을 확인할 수 있어야 한다 고 나와있습니다
             * 그래서 제가 선택한 방법이 isDisabled(is Deleted 가 아니었습니다..) 를 비활성화 처리 해두고
             * 새로운 항목 및 응답들을 save 하는거죠.
             *
             * 업데이트이기는 하나 항목들을 추가할수도, 아예 새로 만들수도 있어서 새로 save 하도록 만들었습니다.
             * */

            /* 선택형 문항의 경우 option list 처리 */
            if (Boolean.TRUE.equals(itemRequest.isChoiceType())) {
                for (ItemOptionRequest optionRequest : itemRequest.getOptionList()) {
                    optionRequest.validationCheck();

                    ItemResponseOption option = ItemResponseOption.createItemResponseOptionRequest(optionRequest.createItemREsponseOptionDto());
                    option.itemKeySet(newItem.getItemSeq());

                    responseOptionRepository.save(option);
                }
            }
        }
    }

    // todo : 응답항목의 이름이나 응답 값을 기반으로 검색하는 부분 추가 필요
    public SurveyDto getSurvey(Long surveySeq) {

        Survey survey = surveyRepository.findById(surveySeq)
                .orElseThrow(() -> new ApiException(ExceptionResponseType.ENTITY_NOT_FNOUND));

        surveyValidCheck(survey);

        SurveyDto surveyDto = survey.of();

        List<SurveyItemDto> surveyItemDtoList = new ArrayList<>();

        List<SurveyItem> surveyItemList = surveyItemRepository.findAllBySurveySeq(surveySeq)
                .orElseThrow(() -> new ApiException(ExceptionResponseType.ENTITY_NOT_FNOUND));

        for (SurveyItem surveyItem : surveyItemList) {
            List<ItemResponseOptionDto> optionList = new ArrayList<>();
            SurveyItemDto itemDto = surveyItem.of();

            List<ItemResponseOption> responseList = responseOptionRepository.findByItemSeq(surveyItem.getItemSeq())
                    .orElseThrow(() -> new ApiException(ExceptionResponseType.ENTITY_NOT_FNOUND));

            for (ItemResponseOption option : responseList) {
                ItemResponseOptionDto optionDto = option.of();
                optionList.add(optionDto);
            }
            itemDto.setResponseOptionList(optionList);
            surveyItemDtoList.add(itemDto);

        }
        surveyDto.setSurveyItemList(surveyItemDtoList);
        return surveyDto;
    }

    public void surveyValidCheck(Survey survey) {
        if (survey == null
                || Boolean.TRUE.equals(survey.getIsDeleted())
                || Boolean.FALSE.equals(survey.getIpAddress().equals(CommonUtils.getRequestIp()))) {
            throw new ApiException(ExceptionResponseType.ENTITY_NOT_FNOUND);
        }
    }
}
