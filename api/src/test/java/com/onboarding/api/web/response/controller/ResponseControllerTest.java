package com.onboarding.api.web.response.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onboarding.api.web.response.dto.request.AnswerRequest;
import com.onboarding.api.web.response.dto.request.SubmitResponseRequest;
import com.onboarding.response.entity.Answer;
import com.onboarding.response.entity.QuestionSnapshot;
import com.onboarding.response.entity.Response;
import com.onboarding.response.entity.ResponseValue;
import com.onboarding.response.facade.ResponseFacade;
import com.onboarding.response.service.ResponseService;
import com.onboarding.survey.entity.Question;
import com.onboarding.survey.entity.Survey;
import com.onboarding.survey.enums.QuestionType;
import com.onboarding.survey.service.SurveyService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ResponseController.class)
class ResponseControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ResponseFacade responseFacade;

  @MockBean
  private ResponseService responseService;

  @MockBean
  private SurveyService surveyService;

  private Survey mockSurvey;
  private Question mockQuestion;
  private QuestionSnapshot mockSnapshot;

  @BeforeEach
  void setUp() {
    // Mock Survey
    mockSurvey = Survey.builder()
        .id(1L)
        .name("Customer Feedback")
        .description("A survey for customer feedback")
        .build();

    // Mock Question and Snapshot
    mockSnapshot = QuestionSnapshot.builder()
        .title("How satisfied are you?")
        .description("Rate from 1 to 5")
        .type(QuestionType.SINGLE_CHOICE)
        .choices(List.of("1", "2", "3", "4", "5"))
        .isRequired(true)
        .build();

    mockQuestion = Question.builder()
        .id(1L)
        .title("How satisfied are you?")
        .description("Rate from 1 to 5")
        .type(QuestionType.SINGLE_CHOICE)
        .survey(mockSurvey)
        .isRequired(true)
        .build();
  }

  @Test
  void getAllResponses_ShouldReturnResponses() throws Exception {
    // Given
    Long surveyId = 1L;

    Response mockResponse = Response.builder()
        .email("test@example.com")
        .answers(List.of(
            Answer.builder()
                .questionSnapshot(mockSnapshot)
                .responseValue(ResponseValue.forChoices(List.of("5")))
                .build()
        ))
        .build();

    Mockito.when(surveyService.findSurveyById(1L))
        .thenReturn(Optional.of(mockSurvey));

    Mockito.when(responseService.findResponsesBySurvey(mockSurvey))
        .thenReturn(List.of(mockResponse));


    // When & Then
    mockMvc.perform(get("/api/v1/{surveyId}", surveyId)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print()) // JSON 응답 출력
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data[0].email").value("test@example.com"));

  }

  @Test
  void submitResponse_ShouldReturn200() throws Exception {
    // Given
    Long surveyId = 1L;

    SubmitResponseRequest mockRequest = SubmitResponseRequest.builder()
        .email("test@example.com")
        .answers(
        List.of(AnswerRequest.builder()
                .questionTitle("How satisfied are you?")
            .questionType("SINGLE_CHOICE")
            .answer(null)
            .choices(List.of("5"))
            .build()))
        .build();

    Mockito.when(surveyService.findSurveyById(surveyId)).thenReturn(Optional.of(mockSurvey));
    Mockito.when(surveyService.findQuestionsBySurveyId(surveyId)).thenReturn(List.of(mockQuestion));

    // When & Then
    mockMvc.perform(post("/api/v1/{surveyId}/responses", surveyId)
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                        {
                          "email": "test@example.com",
                          "answers": [
                            {
                              "questionTitle": "How satisfied are you?",
                              "questionType": "SINGLE_CHOICE",
                              "answer": null,
                              "choices": ["5"]
                            }
                          ]
                        }
                        """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value("Success"));
  }
}