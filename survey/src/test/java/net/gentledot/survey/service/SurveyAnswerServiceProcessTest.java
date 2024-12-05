package net.gentledot.survey.service;

import net.gentledot.survey.dto.request.SearchSurveyAnswerRequest;
import net.gentledot.survey.dto.request.SubmitSurveyAnswer;
import net.gentledot.survey.dto.request.SurveyQuestionOptionRequest;
import net.gentledot.survey.dto.response.SearchSurveyAnswerResponse;
import net.gentledot.survey.exception.SurveyNotFoundException;
import net.gentledot.survey.exception.SurveySubmitValidationException;
import net.gentledot.survey.model.entity.surveybase.Survey;
import net.gentledot.survey.model.entity.surveybase.SurveyQuestion;
import net.gentledot.survey.model.entity.surveybase.SurveyQuestionOption;
import net.gentledot.survey.model.enums.ItemRequired;
import net.gentledot.survey.model.enums.SurveyItemType;
import net.gentledot.survey.repository.SurveyAnswerRepository;
import net.gentledot.survey.repository.SurveyRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class SurveyAnswerServiceProcessTest {

    @Autowired
    SurveyAnswerService surveyAnswerService;

    @Autowired
    SurveyRepository surveyRepository;

    @Autowired
    SurveyAnswerRepository surveyAnswerRepository;

    private Survey survey;

    @BeforeEach
    void setUp() {
        survey = surveyRepository.save(createSurvey());
    }

    private Survey createSurvey() {
        List<SurveyQuestionOption> options = new ArrayList<>();
        options.add(SurveyQuestionOption.from(new SurveyQuestionOptionRequest("Option 1")));
        options.add(SurveyQuestionOption.from(new SurveyQuestionOptionRequest("Option 2")));
        List<SurveyQuestion> questions = new ArrayList<>();
        questions.add(SurveyQuestion.of("Question 1", "Description 1", SurveyItemType.SINGLE_SELECT, ItemRequired.REQUIRED, options));
        questions.add(SurveyQuestion.of("Question 2", "Description 2", SurveyItemType.TEXT, ItemRequired.OPTIONAL, List.of(SurveyQuestionOption.from(new SurveyQuestionOptionRequest("Option 2")))));
        return Survey.of("Survey 1", "Description 1", questions);
    }

    @Test
    void submitSurveyWithValidSurveyIdAndAnswers() {
        List<SubmitSurveyAnswer> answers = new ArrayList<>();
        answers.add(new SubmitSurveyAnswer(
                survey.getQuestions().get(0).getId(), List.of("Question 1")));
        answers.add(new SubmitSurveyAnswer(survey.getQuestions().get(1).getId(),
                List.of("Answer 2")));

        surveyAnswerService.submitSurveyAnswer(survey.getId(), answers);

        Assertions.assertThat(surveyAnswerRepository.findAll()).isNotEmpty();
    }

    @Test
    void failTest_submitSurveyWithInvalidSurveyId() {
        List<SubmitSurveyAnswer> answers = new ArrayList<>();
        answers.add(new SubmitSurveyAnswer(survey.getQuestions().get(0).getId(), List.of("Answer 1")));


        Assertions.assertThatThrownBy(() ->
                        surveyAnswerService.submitSurveyAnswer("invalid-id", answers))
                .isInstanceOf(SurveyNotFoundException.class)
                .hasMessageContaining("요청한 서베이를 찾을 수 없습니다.")
                .satisfies(exception -> {
                    SurveyNotFoundException surveyNotFoundException = (SurveyNotFoundException) exception;
                    Assertions.assertThat(surveyNotFoundException.getServiceError().getCode()).isEqualTo("2001");
                    Assertions.assertThat(surveyNotFoundException.getServiceError().getMessage()).isEqualTo("요청한 서베이를 찾을 수 없습니다.");
                });
    }

    @Test
    void failTest_validateSurveyAnswersWithInvalidQuestionId() {
        // Arrange
        List<SubmitSurveyAnswer> answers = new ArrayList<>();
        answers.add(new SubmitSurveyAnswer(999L, List.of("Answer 1")));

        // Act & Assert
        assertThrows(SurveySubmitValidationException.class, () -> {
            surveyAnswerService.validateSurveyAnswers(survey, answers);
        });
    }

    @Test
    void failTest_validateSurveyAnswersWithoutAnswerOptionIdForRequiredQuestion() {
        // Arrange
        List<SubmitSurveyAnswer> answers = new ArrayList<>();
        answers.add(new SubmitSurveyAnswer(survey.getQuestions().get(0).getId(), Collections.emptyList()));

        // Act & Assert
        assertThrows(SurveySubmitValidationException.class, () -> {
            surveyAnswerService.validateSurveyAnswers(survey, answers);
        });
    }

    @Test
    void failTest_validateSurveyAnswersWithMultipleOptionsForSingleSelectQuestion() {
        // Arrange
        List<SubmitSurveyAnswer> answers = new ArrayList<>();
        answers.add(new SubmitSurveyAnswer(survey.getQuestions().get(0).getId(), List.of("Option 1", "Option 2")));

        // Act & Assert
        assertThrows(SurveySubmitValidationException.class, () -> {
            surveyAnswerService.validateSurveyAnswers(survey, answers);
        });
    }

    @Test
    void getSurveyAnswersWithValidRequest() {
        List<SubmitSurveyAnswer> answers = new ArrayList<>();
        answers.add(new SubmitSurveyAnswer(
                survey.getQuestions().get(0).getId(),
                List.of("Option 1")));
        answers.add(new SubmitSurveyAnswer(survey.getQuestions().get(1).getId(), List.of("Answer 2")));

        surveyAnswerService.submitSurveyAnswer(survey.getId(), answers);

        SearchSurveyAnswerRequest request = SearchSurveyAnswerRequest.builder()
                .surveyId(survey.getId())
                .build();

        SearchSurveyAnswerResponse response = surveyAnswerService.getSurveyAnswers(request);


        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.surveyId()).isEqualTo(survey.getId());
        Assertions.assertThat(response.answerList()).hasSize(1);
    }

    @Test
    void getSurveyAnswersWithFilter() {
        List<SubmitSurveyAnswer> answers = new ArrayList<>();
        answers.add(new SubmitSurveyAnswer(
                survey.getQuestions().get(0).getId(),
                List.of("Option 1")));
        answers.add(new SubmitSurveyAnswer(survey.getQuestions().get(1).getId(), List.of("Answer 2")));

        surveyAnswerService.submitSurveyAnswer(survey.getId(), answers);

        SearchSurveyAnswerRequest request = SearchSurveyAnswerRequest.builder()
                .surveyId(survey.getId())
                .questionName("Question 1")
                .build();

        SearchSurveyAnswerResponse response = surveyAnswerService.getSurveyAnswers(request);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.surveyId()).isEqualTo(survey.getId());
        Assertions.assertThat(response.answerList()).hasSize(1);
    }

}