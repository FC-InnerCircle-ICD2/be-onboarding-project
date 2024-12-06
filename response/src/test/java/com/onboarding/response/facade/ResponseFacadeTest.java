package com.onboarding.response.facade;

import com.onboarding.core.global.utils.RedisLock;
import com.onboarding.response.dto.response.ResponseDTO;
import com.onboarding.response.entity.Answer;
import com.onboarding.response.entity.QuestionSnapshot;
import com.onboarding.response.entity.Response;
import com.onboarding.response.entity.ResponseValue;
import com.onboarding.response.object.AnswerObject;
import com.onboarding.response.object.ResponseObject;
import com.onboarding.response.service.ResponseService;
import com.onboarding.survey.entity.Question;
import com.onboarding.survey.entity.Survey;
import com.onboarding.survey.enums.QuestionType;
import com.onboarding.survey.service.QuestionService;
import com.onboarding.survey.service.SurveyService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class ResponseFacadeTest {

  @InjectMocks
  private ResponseFacade responseFacade;

  @Mock
  private SurveyService surveyService;

  @Mock
  private QuestionService questionService;

  @Mock
  private ResponseService responseService;

  @Mock
  private RedisLock redisLock;

  @Test
  void submitResponse_ShouldSaveValidResponse() {
    // Given
    Long surveyId = 1L;
    String email = "test@example.com";

    Survey survey = Survey.builder()
        .id(surveyId)
        .name("Customer Feedback")
        .description("A survey to gather feedback")
        .questions(List.of(
            Question.builder()
                .title("How satisfied are you?")
                .type(QuestionType.SINGLE_CHOICE)
                .isRequired(true)
                .choices(List.of("1", "2", "3", "4", "5"))
                .build()
        ))
        .build();

    ResponseObject responseObject = ResponseObject.builder()
        .email(email)
        .answerObjects(List.of(
            AnswerObject.builder()
                .questionTitle("How satisfied are you?")
                .choices(List.of("5"))
                .build()
        ))
        .build();

    // Mock 설정
    Mockito.when(redisLock.lock("survey:lock:1", 10)).thenReturn(true);
    Mockito.when(surveyService.findSurveyById(surveyId)).thenReturn(Optional.of(survey));
    Mockito.when(questionService.findQuestionsBySurveyId(surveyId)).thenReturn(survey.getQuestions());
    Mockito.when(responseService.existsBySurveyIdAndEmail(surveyId, email)).thenReturn(false);
    Mockito.doNothing().when(responseService).saveResponse(Mockito.any(), Mockito.anyString(), Mockito.anyList());

    // When
    responseFacade.submitResponse(surveyId, responseObject);

    // Then
    Mockito.verify(redisLock, Mockito.times(1)).lock("survey:lock:1", 10);
    Mockito.verify(responseService, Mockito.times(1)).saveResponse(Mockito.any(), Mockito.anyString(), Mockito.anyList());
    Mockito.verify(redisLock, Mockito.times(1)).unlock("survey:lock:1");
  }




  @Test
  void getAllResponses_ShouldReturnResponses() {
    // Given
    Long surveyId = 1L;

    Survey survey = Survey.builder()
        .id(surveyId)
        .name("Customer Feedback")
        .description("A survey to gather feedback")
        .build();

    Response response = Response.builder()
        .email("test@example.com")
        .answers(List.of(
            Answer.builder()
                .questionSnapshot(new QuestionSnapshot(
                    "How satisfied are you?",
                    "Rate from 1 to 5",
                    QuestionType.SINGLE_CHOICE,
                    List.of("1", "2", "3", "4", "5"),
                    true
                ))
                .responseValue(ResponseValue.forChoices(List.of("5")))
                .build()
        ))
        .build();

    Mockito.when(surveyService.findSurveyById(surveyId)).thenReturn(Optional.of(survey));
    Mockito.when(responseService.findResponsesBySurvey(survey)).thenReturn(List.of(response));

    // When
    List<ResponseDTO> responses = responseFacade.getAllResponses(surveyId);

    // Then
    Assertions.assertEquals(1, responses.size());
    Assertions.assertEquals("test@example.com", responses.get(0).getEmail());
  }
}
