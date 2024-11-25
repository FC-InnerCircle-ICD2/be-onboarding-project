package net.gentledot.survey.service;

import net.gentledot.survey.dto.request.SurveyGenerateRequest;
import net.gentledot.survey.dto.request.SurveyQuestionOptionRequest;
import net.gentledot.survey.dto.request.SurveyQuestionRequest;
import net.gentledot.survey.model.entity.Survey;
import net.gentledot.survey.model.entity.SurveyQuestion;
import net.gentledot.survey.model.entity.SurveyQuestionOption;
import net.gentledot.survey.model.enums.ItemRequired;
import net.gentledot.survey.model.enums.SurveyItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class SurveyServiceTest {
    SurveyGenerateRequest surveyRequest;

    @BeforeEach
    void setUp() {
        surveyRequest = SurveyGenerateRequest.builder()
                .name("test")
                .description("survey description")
                .questions(List.of(
                        SurveyQuestionRequest.builder()
                                .itemName("question1")
                                .itemDescription("question1 description")
                                .itemType(SurveyItemType.MULTI_SELECT)
                                .required(ItemRequired.REQUIRED)
                                .options(List.of(
                                        new SurveyQuestionOptionRequest("option1"),
                                        new SurveyQuestionOptionRequest("option2")
                                )).build()
                )).build();
    }

    @DisplayName("service 구상 및 제약조건 설정 로직 구상")
    @Test
    void createServiceTest() {

        /*
         *  필요한 제약 조건
         * */
        List<SurveyQuestion> questions = surveyRequest.getQuestions().stream()
                .map(this::convertToSurveyQuestion)
                .collect(Collectors.toList());

        Survey survey = Survey.of(
                surveyRequest.getName(),
                surveyRequest.getDescription(),
                questions
        );

        assertThat(survey).isNotNull();

    }

    private SurveyQuestion convertToSurveyQuestion(SurveyQuestionRequest questionRequest) {
        List<SurveyQuestionOption> options = questionRequest.getOptions()
                .stream()
                .map(this::convertToSurveyItemOption)
                .collect(Collectors.toList());

        return SurveyQuestion.of(
                questionRequest.getItemName(),
                questionRequest.getItemDescription(),
                questionRequest.getItemType(),
                questionRequest.getRequired(),
                options
        );
    }

    private SurveyQuestionOption convertToSurveyItemOption(SurveyQuestionOptionRequest optionRequest) {
        return SurveyQuestionOption.of(optionRequest.getOptionText());
    }

}