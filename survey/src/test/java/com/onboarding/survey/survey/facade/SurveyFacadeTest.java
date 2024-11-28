package com.onboarding.survey.survey.facade;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.onboarding.core.global.exception.CustomException;
import com.onboarding.core.global.exception.enums.ErrorCode;
import com.onboarding.survey.entity.Question;
import com.onboarding.survey.entity.Survey;
import com.onboarding.survey.enums.QuestionType;
import com.onboarding.survey.facade.SurveyFacade;
import com.onboarding.survey.service.create.CreateSurveyService;
import com.onboarding.survey.service.read.ReadQuestionService;
import com.onboarding.survey.service.read.ReadSurveyService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class SurveyFacadeTest {
  @Mock
  private CreateSurveyService createSurveyService;

  @Mock
  private ReadSurveyService readSurveyService;

  @Mock
  private ReadQuestionService readQuestionService;

  @InjectMocks
  private SurveyFacade surveyFacade;

  private Survey survey;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    // Survey 객체 생성 및 초기화
    Question question1 = new Question(1L, "Question 1", "Desc 1", QuestionType.SINGLE_CHOICE, true, 1, null, List.of("Option 1", "Option 2"));
    Question question2 = new Question(2L, "Question 2", "Desc 2", QuestionType.MULTIPLE_CHOICE, true, 2, null, List.of("Option A", "Option B"));
    survey = new Survey(1L, "Survey Test", "Survey Desc", List.of(question1, question2));
    question1.setSurvey(survey);
    question2.setSurvey(survey);
  }

  @Test
  void testSwapQuestionOrderSuccessfully() {
    // Mocking survey retrieval
    when(readSurveyService.getSurveyById(1L)).thenReturn(survey);
    when(readQuestionService.getQuestionById(1L)).thenReturn(survey.getQuestions().get(0));
    when(readQuestionService.getQuestionById(2L)).thenReturn(survey.getQuestions().get(1));

    // Swap order of question 1 and question 2
    surveyFacade.swapQuestionOrder(1L, 1L, 2L);

    // 검증: Question 1과 Question 2의 순서가 서로 교환되었는지 확인
    Question updatedQuestion1 = survey.getQuestions().stream()
        .filter(q -> q.getId().equals(1L))
        .findFirst()
        .orElseThrow();
    Question updatedQuestion2 = survey.getQuestions().stream()
        .filter(q -> q.getId().equals(2L))
        .findFirst()
        .orElseThrow();

    assertThat(updatedQuestion1.getOrderIndex()).isEqualTo(2);
    assertThat(updatedQuestion2.getOrderIndex()).isEqualTo(1);
  }

  @Test
  void testSwapQuestionOrderWithInvalidQuestion() {
    // Mocking survey retrieval
    when(readSurveyService.getSurveyById(1L)).thenReturn(survey);
    when(readQuestionService.getQuestionById(3L)).thenThrow(new CustomException(ErrorCode.QUESTION_NOT_FOUND));

    // 존재하지 않는 Question ID로 교환 시도 시 예외 발생 여부 확인
    CustomException exception = assertThrows(CustomException.class, () -> {
      surveyFacade.swapQuestionOrder(1L, 1L, 3L);
    });

    assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.QUESTION_NOT_FOUND);
  }

}