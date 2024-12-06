package com.onboarding.response.facade;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.onboarding.core.global.exception.CustomException;
import com.onboarding.core.global.exception.enums.ErrorCode;
import com.onboarding.core.global.utils.RedisLock;
import com.onboarding.response.dto.response.ResponseDTO;
import com.onboarding.response.entity.Response;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ResponseFacadeTest {
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

  private Survey testSurvey;
  private Question testQuestion;

  @BeforeEach
  void setup() {
    testSurvey = new Survey("Test Survey", "Description");
    testQuestion = new Question("Test Question", "Description", QuestionType.SHORT_ANSWER, true, null);
    testSurvey.addQuestion(testQuestion);

    // RedisLock Mock 설정 - 유연한 매칭
    when(redisLock.lock(anyString(), anyInt())).thenReturn(true);
    doNothing().when(redisLock).unlock(anyString());

    // SurveyService Mock 설정
    when(surveyService.findSurveyById(anyLong())).thenReturn(Optional.of(testSurvey));

    // QuestionService Mock 설정
    when(questionService.findQuestionsBySurveyId(anyLong())).thenReturn(List.of(testQuestion));
  }

  @Test
  void submitResponse_success() {
    // Given
    ResponseObject responseObject = new ResponseObject(
        "user@example.com",
        List.of(new AnswerObject("Test Question", "SHORT_ANSWER", true, "Sample Answer", null))
    );

    when(responseService.saveResponse(any(Survey.class), anyString(), anyList()))
        .thenReturn(new Response());

    // When & Then
    assertDoesNotThrow(() -> responseFacade.submitResponse(1L, responseObject));
    verify(responseService, times(1)).saveResponse(any(Survey.class), eq("user@example.com"), anyList());
  }


  @Test
  void submitResponse_fail_requiredQuestionNotAnswered() {
    // Given
    ResponseObject responseObject = new ResponseObject("user@example.com", List.of()); // No answers provided

    // When & Then
    CustomException exception = assertThrows(CustomException.class, () -> responseFacade.submitResponse(1L, responseObject));
    assertEquals(ErrorCode.MISSING_REQUIRED_ANSWER, exception.getErrorCode());
  }

  @Test
  void getAllResponses_success() {
    // Given
    Response response = new Response();
    when(responseService.findResponsesBySurvey(any(Survey.class))).thenReturn(List.of(response));

    // When
    List<ResponseDTO> responses = responseFacade.getAllResponses(1L);

    // Then
    assertEquals(1, responses.size());
    verify(responseService, times(1)).findResponsesBySurvey(any(Survey.class));
  }

  @Test
  void searchResponses_success_withParams() {
    // Given
    Response response = new Response();
    when(responseService.searchResponses(any(Survey.class), anyString(), anyString())).thenReturn(List.of(response));

    // When
    List<ResponseDTO> responses = responseFacade.searchResponses(1L, "Test Question", "Test Answer");

    // Then
    assertEquals(1, responses.size());
    verify(responseService, times(1)).searchResponses(any(Survey.class), eq("Test Question"), eq("Test Answer"));
  }
}