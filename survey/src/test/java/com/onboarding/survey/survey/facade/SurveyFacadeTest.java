package com.onboarding.survey.survey.facade;

import com.onboarding.survey.dto.response.SurveyWithQuestionsDTO;
import com.onboarding.survey.entity.Question;
import com.onboarding.survey.entity.Survey;
import com.onboarding.survey.enums.QuestionType;
import com.onboarding.survey.facade.SurveyFacade;
import com.onboarding.survey.object.QuestionObject;
import com.onboarding.survey.object.SurveyObject;
import com.onboarding.survey.service.QuestionService;
import com.onboarding.survey.service.SurveyService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;


@ExtendWith(MockitoExtension.class)
public class SurveyFacadeTest {

  @InjectMocks
  private SurveyFacade surveyFacade;

  @Mock
  private SurveyService surveyService;

  @Mock
  private QuestionService questionService;

  @Test
  void createSurvey_ShouldSaveSurveyWithQuestions() {
    // Given
    SurveyObject surveyObject = SurveyObject.builder()
        .surveyName("Customer Feedback")
        .surveyDescription("A survey to gather feedback")
        .questions(List.of(
            QuestionObject.builder()
                .title("How satisfied are you?")
                .description("Rate from 1 to 5")
                .type(QuestionType.SINGLE_CHOICE)
                .isRequired(true)
                .choices(List.of("1", "2", "3", "4", "5"))
                .build()
        ))
        .build();

    Question mockQuestion = Question.builder()
        .title("How satisfied are you?")
        .description("Rate from 1 to 5")
        .type(QuestionType.SINGLE_CHOICE)
        .isRequired(true)
        .choices(List.of("1", "2", "3", "4", "5"))
        .build();

    // Mock 설정
    Mockito.when(questionService.validateAndCreateQuestion(Mockito.any(Question.class))).thenReturn(mockQuestion);
    Mockito.doNothing().when(surveyService).createSurvey(Mockito.any(Survey.class));

    // When
    surveyFacade.createSurvey(surveyObject);

    // Then
    Mockito.verify(surveyService, Mockito.times(1)).createSurvey(Mockito.any(Survey.class));
    Mockito.verify(questionService, Mockito.times(1)).validateAndCreateQuestion(Mockito.any(Question.class));
  }



  @Test
  void updateSurvey_ShouldUpdateExistingSurvey() {
    // Given
    Long surveyId = 1L;

    Survey existingSurvey = Survey.builder()
        .id(surveyId)
        .name("Old Survey Name")
        .description("Old Description")
        .questions(new ArrayList<>())
        .build();

    SurveyObject updatedSurveyObject = SurveyObject.builder()
        .surveyName("Updated Survey Name")
        .surveyDescription("Updated Description")
        .questions(List.of(
            QuestionObject.builder()
                .title("Updated Question Title")
                .description("Updated Description")
                .type(QuestionType.SHORT_ANSWER)
                .isRequired(true)
                .build()
        ))
        .build();

    Mockito.when(surveyService.getSurveyById(surveyId)).thenReturn(existingSurvey);

    // When
    surveyFacade.updateSurvey(surveyId, updatedSurveyObject);

    // Then
    Mockito.verify(surveyService, Mockito.times(1)).getSurveyById(surveyId);
    Mockito.verify(surveyService, Mockito.times(1)).createSurvey(Mockito.any(Survey.class));
  }

  @Test
  void getSurveyByIdWithQuestions_ShouldReturnSurveyWithQuestions() {
    // Given
    Long surveyId = 1L;

    Survey survey = Survey.builder()
        .id(surveyId)
        .name("Customer Feedback")
        .description("A survey to gather feedback")
        .questions(List.of(
            Question.builder()
                .id(1L)
                .title("Question 1")
                .description("Description")
                .type(QuestionType.SHORT_ANSWER)
                .isRequired(true)
                .build()
        ))
        .build();

    Mockito.when(surveyService.findSurveyById(surveyId)).thenReturn(Optional.of(survey));

    // When
    SurveyWithQuestionsDTO result = surveyFacade.getSurveyByIdWithQuestions(surveyId);

    // Then
    Assertions.assertEquals("Customer Feedback", result.getName());
    Assertions.assertEquals(1, result.getQuestions().size());
    Mockito.verify(surveyService, Mockito.times(1)).findSurveyById(surveyId);
  }
}
