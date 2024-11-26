package net.gentledot.survey.service;

import net.gentledot.survey.dto.request.SurveyGenerateRequest;
import net.gentledot.survey.dto.request.SurveyQuestionOptionRequest;
import net.gentledot.survey.dto.request.SurveyQuestionRequest;
import net.gentledot.survey.exception.ServiceError;
import net.gentledot.survey.exception.SurveyCreationException;
import net.gentledot.survey.model.entity.Survey;
import net.gentledot.survey.model.entity.SurveyQuestion;
import net.gentledot.survey.model.entity.SurveyQuestionOption;
import net.gentledot.survey.model.enums.ItemRequired;
import net.gentledot.survey.model.enums.SurveyItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SurveyServiceTest {
    SurveyGenerateRequest surveyRequest;
    ;

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
        assertThat(survey.getId()).isNotBlank();
        assertThat(survey.getName()).isEqualTo(surveyRequest.getName());
        assertThat(survey.getDescription()).isEqualTo(surveyRequest.getDescription());
    }

    @DisplayName("service 구상 및 제약조건 설정 로직 구상 - SurveyRequest 로 Question과 Option 생성되도록 변경")
    @Test
    void createServiceTestVer2() {
        Survey survey = generateSurvey(surveyRequest);

        assertThat(survey).isNotNull();
        assertThat(survey.getId()).isNotBlank();
        assertThat(survey.getName()).isEqualTo(surveyRequest.getName());
        assertThat(survey.getDescription()).isEqualTo(surveyRequest.getDescription());
    }

    @Test
    void testFailWhenRequestWithNoQuestion() {
        SurveyGenerateRequest requestWithNoQuestions = SurveyGenerateRequest.builder()
                .name("no questions!")
                .description("description")
                .questions(Collections.emptyList())
                .build();

        assertThatThrownBy(() -> generateSurvey(requestWithNoQuestions))
                .isInstanceOf(SurveyCreationException.class);

    }


    private Survey generateSurvey(SurveyGenerateRequest surveyRequest) {
        if (surveyRequest.getQuestions().isEmpty()) {
            throw new SurveyCreationException(ServiceError.SURVEY_CREATION_INSUFFICIENT_QUESTIONS);
        }

        List<SurveyQuestion> questions = convertToSurveyQuestions(surveyRequest.getQuestions());

        return Survey.of(
                surveyRequest.getName(),
                surveyRequest.getDescription(),
                questions
        );
    }

    private List<SurveyQuestion> convertToSurveyQuestions(List<SurveyQuestionRequest> questionRequests) {
        return questionRequests.stream()
                .map(SurveyQuestion::from)
                .collect(Collectors.toList());
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