package ic2.onboarding.survey.service;

import ic2.onboarding.survey.dto.*;
import ic2.onboarding.survey.global.BizException;
import ic2.onboarding.survey.global.ErrorCode;
import ic2.onboarding.survey.repository.SurveyRepository;
import ic2.onboarding.survey.entity.Survey;
import ic2.onboarding.survey.entity.SurveyItem;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Validated
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SurveyService {

    private final SurveyRepository surveyRepository;


    @Transactional
    public SurveyCreationResponse createSurvey(@Valid SurveyCreationRequest request) {

        // 기본 정보 -> Survey Entity
        Survey survey = new Survey(request.basic().name(), request.basic().description());

        // 항목 정보 -> SurveyItem Entity List
        List<SurveyItem> surveyItems = request.items()
                .stream()
                .map(SurveyService::mapSurveyItem)
                .toList();

        // 저장
        survey.addAllItems(surveyItems);
        Survey saved = surveyRepository.save(survey);

        return SurveyCreationResponse.fromEntity(saved);
    }


    @Transactional
    public SurveyUpdateResponse updateSurvey(Long id, @Valid SurveyUpdateRequest request) {

        // 기존 설문 찾기
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> new BizException(ErrorCode.NOT_FOUND));

        // 설문 기본정보 변경
        survey.update(request.newSurveyName(), request.newSurveyDescription());

        // 변경 ITEM ID 목록 추출
        Set<Long> requestItemIds = request.getItemIds();

        // 목록에서 사라진 ITEM 지운다.
        survey.removeNotContainedItemsInIdSet(requestItemIds);

        // 신규항목 추가 또는 기존항목 수정
        request.items().forEach(formItem -> Optional.ofNullable(formItem.id())
                .ifPresentOrElse(
                        // 변경할 ID 존재시 UPDATE
                        targetId -> survey.updateItemInfo(targetId, mapSurveyItem(formItem)),

                        // 없다면 INSERT
                        () -> survey.addItem(mapSurveyItem(formItem))
                )
        );

        surveyRepository.flush();
        return SurveyUpdateResponse.fromEntity(survey);
    }


    private static SurveyItem mapSurveyItem(SurveyFormItem formItem) {

        return new SurveyItem(
                formItem.name(),
                formItem.description(),
                formItem.inputTypeAsEnum(),
                formItem.required(),
                formItem.choicesAsString());
    }

}
