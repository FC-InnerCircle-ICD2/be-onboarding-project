package ic2.onboarding.survey.service;

import ic2.onboarding.survey.FixtureUtil;
import ic2.onboarding.survey.dto.*;
import ic2.onboarding.survey.entity.Survey;
import ic2.onboarding.survey.entity.SurveyItem;
import ic2.onboarding.survey.global.BizException;
import ic2.onboarding.survey.global.ItemInputType;
import ic2.onboarding.survey.repository.SurveyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SurveyServiceUnitTest {

    @Mock
    private SurveyRepository surveyRepository;

    @InjectMocks
    private SurveyService surveyService;


    @Test
    @DisplayName("설문조사 생성 성공")
    void createSurvey_success() {

        // given
        int size = 5;

        // 생성할 데이터
        List<String> choiceList = FixtureUtil.getDefaultFixtureMonkey()
                .giveMeBuilder(String.class)
                .set(FixtureUtil.getKoreanStringArbitrary())
                .sampleList(3);

        Survey survey = FixtureUtil.surveyArbitraryBuilder().sample();
        List<SurveyItem> surveyItems = FixtureUtil.surveyItemArbitraryBuilder()
                .set("choices", String.join("|", choiceList))
                .sampleList(size);

        when(surveyRepository.save(any(Survey.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        SurveyForm basic = new SurveyForm(null, survey.getName(), survey.getDescription());
        List<SurveyFormItem> formItems = surveyItems.stream()
                .map(SurveyFormItem::fromEntity)
                .toList();

        // when
        SurveyCreationRequest request = new SurveyCreationRequest(basic, formItems);

        SurveyCreationResponse response = surveyService.createSurvey(request);

        // 결과
        SurveyForm surveyForm = response.basic();
        List<SurveyFormItem> surveyFormItems = response.items();

        // then
        assertEquals(survey.getName(), surveyForm.name());
        assertEquals(survey.getDescription(), surveyForm.description());
        assertEquals(surveyItems.size(), surveyFormItems.size());
        for (int i = 0; i < surveyFormItems.size(); i++) {
            SurveyItem surveyItem = surveyItems.get(i);
            SurveyFormItem surveyFormItem = surveyFormItems.get(i);

            assertEquals(surveyItem.getId(), surveyFormItem.id());
            assertEquals(surveyItem.getName(), surveyFormItem.name());
            assertEquals(surveyItem.getDescription(), surveyFormItem.description());
            assertEquals(surveyItem.getInputType().name(), surveyFormItem.inputType());
            assertEquals(surveyItem.getRequired(), surveyFormItem.required());
            assertEquals(surveyItem.getChoiceList(), surveyFormItem.choices());
        }

    }


    @Test
    @DisplayName("설문조사 수정 실패 - 기존 설문 없음")
    void updateSurvey_failure_notFound() {

        // given
        when(surveyRepository.findById(any()))
                .thenReturn(Optional.empty());

        // when, then
        assertThrows(BizException.class, () -> surveyService.updateSurvey(1L, null));
    }


    @Test
    @DisplayName("설문조사 수정 성공")
    void updateSurvey_success() {

        // given
        int size = 5;

        // 기존 데이터 가정
        Survey stored = FixtureUtil.surveyArbitraryBuilder().sample();
        List<SurveyItem> storedSurveyItems = FixtureUtil.surveyItemArbitraryBuilder()
                .sampleList(size);

        stored.addAllItems(storedSurveyItems);

        when(surveyRepository.findById(any()))
                .thenReturn(Optional.of(stored));

        // 새로운 데이터
        Survey surveyToBeReplaced = FixtureUtil.surveyArbitraryBuilder().sample();
        List<SurveyItem> surveyItemsToBeReplaced = new ArrayList<>(storedSurveyItems); // 기존값 복사
        surveyToBeReplaced.addAllItems(surveyItemsToBeReplaced);

        // 항목 앞, 뒤 2개 제거
        storedSurveyItems.removeFirst();
        storedSurveyItems.removeLast();
        surveyItemsToBeReplaced.removeFirst();
        surveyItemsToBeReplaced.removeLast();

        // 1개 수정
        SurveyItem first = surveyItemsToBeReplaced.getFirst();
        String updateName = "updateName";
        String updateDesc = "updateDesc";
        boolean updateRequired = true;
        String updateInputType = "MULTIPLE_CHOICE";
        String updateChoices = "A|B|C";
        first.update(new SurveyItem(
                null,
                null,
                updateName,
                updateDesc,
                ItemInputType.fromString(updateInputType),
                updateRequired,
                updateChoices));

        SurveyForm basic = new SurveyForm(null, surveyToBeReplaced.getName(), surveyToBeReplaced.getDescription());
        List<SurveyFormItem> formItems = surveyItemsToBeReplaced.stream()
                .map(SurveyFormItem::fromEntity)
                .toList();

        SurveyUpdateRequest request = new SurveyUpdateRequest(basic, formItems);

        // when
        SurveyUpdateResponse response = surveyService.updateSurvey(0L, request);

        SurveyForm surveyForm = response.basic();
        List<SurveyFormItem> surveyFormItems = response.items();

        // then

        // 수정된 값 검증
        assertEquals(surveyToBeReplaced.getName(), surveyForm.name());
        assertEquals(surveyToBeReplaced.getDescription(), surveyForm.description());
        assertEquals(size - 2, surveyFormItems.size());

        SurveyFormItem item0 = surveyFormItems.get(0);
        assertEquals(updateName, item0.name());
        assertEquals(updateDesc, item0.description());
        assertEquals(updateRequired, item0.required());
        assertEquals(updateInputType, item0.inputType());
        assertEquals(updateChoices, String.join("|", item0.choices()));

        // 기존 정보 검증
        for (int i = 1; i < surveyFormItems.size(); i++) {
            SurveyItem surveyItem = storedSurveyItems.get(i);
            SurveyFormItem surveyFormItem = surveyFormItems.get(i);

            assertEquals(surveyItem.getId(), surveyFormItem.id());
            assertEquals(surveyItem.getName(), surveyFormItem.name());
            assertEquals(surveyItem.getDescription(), surveyFormItem.description());
            assertEquals(surveyItem.getInputType().name(), surveyFormItem.inputType());
            assertEquals(surveyItem.getRequired(), surveyFormItem.required());
            assertEquals(surveyItem.getChoiceList(), surveyFormItem.choices());
        }

    }

}