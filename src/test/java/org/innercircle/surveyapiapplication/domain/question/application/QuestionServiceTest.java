package org.innercircle.surveyapiapplication.domain.question.application;

import org.innercircle.surveyapiapplication.domain.question.domain.MultiChoiceQuestion;
import org.innercircle.surveyapiapplication.domain.question.domain.Question;
import org.innercircle.surveyapiapplication.domain.question.domain.type.QuestionType;
import org.innercircle.surveyapiapplication.domain.question.fixture.QuestionFixture;
import org.innercircle.surveyapiapplication.domain.survey.domain.Survey;
import org.innercircle.surveyapiapplication.domain.survey.fixture.SurveyFixture;
import org.innercircle.surveyapiapplication.domain.survey.infrastructure.SurveyRepository;
import org.innercircle.surveyapiapplication.domain.survey.presentation.dto.QuestionUpdateRequest;
import org.innercircle.surveyapiapplication.global.exception.CustomException;
import org.innercircle.surveyapiapplication.global.exception.CustomResponseStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("local")
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class QuestionServiceTest {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private QuestionService questionService;

    @Test
    @DisplayName("[SUCCESS] 설문조사에 설문항목을 등록할 수 있다")
    void createSingleQuestion() {
        // given
        Question question = QuestionFixture.createShortAnswerQuestion();
        Survey survey = SurveyFixture.createSurvey(List.of(question));

        // when
        Survey savedSurvey = surveyRepository.save(survey);
        Question targetQuestion = QuestionFixture.createShortAnswerQuestion();
        questionService.createQuestion(savedSurvey.getId(), targetQuestion);

        Question savedQuestion = questionService.findByIdAndVersion(targetQuestion.getId(), targetQuestion.getVersion());

        // then
        assertThat(savedQuestion.getId()).isEqualTo(targetQuestion.getId());
        assertThat(savedQuestion.getVersion()).isEqualTo(targetQuestion.getVersion());
        assertThat(savedQuestion.getName()).isEqualTo(targetQuestion.getName());
        assertThat(savedQuestion.getDescription()).isEqualTo(targetQuestion.getDescription());
        assertThat(savedQuestion.getType()).isEqualTo(targetQuestion.getType());
        assertThat(savedQuestion.getSurveyId()).isEqualTo(targetQuestion.getSurveyId());
    }

    @Test
    @DisplayName("[FAILURE] 설문조사에 10개의 설문 항목이 있는경우 설문항목 추가 시 예외가 발생한다.")
    void failToCreateSingleQuestionWhenSurveyAlreadyHaveTenQuestions() {
        // given
        List<Question> questions = List.of(
            QuestionFixture.createShortAnswerQuestion(1L),
            QuestionFixture.createShortAnswerQuestion(2L),
            QuestionFixture.createShortAnswerQuestion(3L),
            QuestionFixture.createShortAnswerQuestion(4L),
            QuestionFixture.createShortAnswerQuestion(5L),
            QuestionFixture.createShortAnswerQuestion(6L),
            QuestionFixture.createShortAnswerQuestion(7L),
            QuestionFixture.createShortAnswerQuestion(8L),
            QuestionFixture.createShortAnswerQuestion(9L),
            QuestionFixture.createShortAnswerQuestion(10L)
        );

        Survey survey = SurveyFixture.createSurvey(questions);
        Survey savedSurvey = surveyRepository.save(survey);

        // when & then
        Question targetQuestion = QuestionFixture.createShortAnswerQuestion(11L);

        assertThatThrownBy(() -> questionService.createQuestion(savedSurvey.getId(), targetQuestion))
            .isInstanceOf(CustomException.class)
            .extracting(e -> ((CustomException) e).getStatus())
            .isEqualTo(CustomResponseStatus.QUESTION_SIZE_FULL);
    }

    @Test
    @DisplayName("[SUCCESS] 설문항목을 수정할 수 있다.")
    void updateQuestion() {
        // given
        Question question = QuestionFixture.createShortAnswerQuestion();
        Survey survey = SurveyFixture.createSurvey(List.of(question));


        Survey savedSurvey = surveyRepository.save(survey);
        Question targetQuestion = QuestionFixture.createShortAnswerQuestion();
        questionService.createQuestion(savedSurvey.getId(), targetQuestion);

        QuestionUpdateRequest request = new QuestionUpdateRequest(
            "변경설문항목이름",
            "변경설문항목설명",
            QuestionType.MULTI_CHOICE_ANSWER,
            true,
            List.of("설문항목옵션1", "설문항목옵션2", "설문항목옵션3", "설문항목옵션4", "설문항목옵션5")
        );

        // when
        MultiChoiceQuestion updatedQuestion = (MultiChoiceQuestion) questionService.updateQuestion(targetQuestion.getId(), request);

        // then
        assertThat(updatedQuestion.getId()).isEqualTo(targetQuestion.getId());
        assertThat(updatedQuestion.getVersion()).isEqualTo(targetQuestion.getVersion() + 1);
        assertThat(updatedQuestion.getSurveyId()).isEqualTo(targetQuestion.getSurveyId());

        assertThat(updatedQuestion.getName()).isEqualTo(request.name());
        assertThat(updatedQuestion.getDescription()).isEqualTo(request.description());
        assertThat(updatedQuestion.getType()).isEqualTo(request.type());
        assertThat(updatedQuestion.isRequired()).isEqualTo(request.required());
        assertThat(updatedQuestion.getOptions()).containsAll(request.options());
    }

}