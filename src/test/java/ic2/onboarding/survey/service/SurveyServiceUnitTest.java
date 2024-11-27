package ic2.onboarding.survey.service;

import ic2.onboarding.survey.FixtureUtil;
import ic2.onboarding.survey.dto.SurveyCreationRequest;
import ic2.onboarding.survey.dto.SurveyCreationResponse;
import ic2.onboarding.survey.dto.SurveyForm;
import ic2.onboarding.survey.dto.SurveyFormItem;
import ic2.onboarding.survey.entity.Survey;
import ic2.onboarding.survey.entity.SurveyItem;
import ic2.onboarding.survey.repository.SurveyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

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

        SurveyCreationRequest request = new SurveyCreationRequest(basic, formItems);

        // when
        SurveyCreationResponse response = surveyService.createSurvey(request);

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

}