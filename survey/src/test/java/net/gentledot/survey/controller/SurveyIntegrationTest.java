package net.gentledot.survey.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import net.gentledot.survey.dto.request.SurveyCreateRequest;
import net.gentledot.survey.dto.request.SurveyQuestionOptionRequest;
import net.gentledot.survey.dto.request.SurveyQuestionRequest;
import net.gentledot.survey.dto.request.SurveyUpdateRequest;
import net.gentledot.survey.model.enums.ItemRequired;
import net.gentledot.survey.model.enums.SurveyItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SurveyIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void createSurvey() {
        String requestBody = """
                {
                    "name": "나만의 설문",
                    "description": "간단한 설문입니다.",
                    "questions": [
                      {
                        "question": "이름을 알려주세요",
                        "description": "당신의 이름은 무엇입니까?",
                        "type": "TEXT",
                        "required": "REQUIRED"
                
                      },
                      {
                        "question": "오늘의 기분을 알려주세요",
                        "description": "가벼운 질문부터",
                        "type": "SINGLE_SELECT",
                        "required": "REQUIRED",
                        "options": [
                          {
                            "option": "좋아요"
                          },
                          {
                            "option": "안좋아요"
                          }
                        ]
                      },
                      {
                        "question": "잠에 드는 시간대가 언제 즈음인가요?",
                        "description": "23시에서 01시까지",
                        "type": "MULTI_SELECT",
                        "required": "REQUIRED",
                        "options": [
                          {
                            "option": "23:00"
                          },
                          {
                            "option": "24:00"
                          },
                          {
                            "option": "01:00"
                          }
                        ]
                      },
                      {
                        "question": "설문이 어떠셨는지 의견을 남겨주세요",
                        "description": "간단한 의견이라도 좋습니다. :)",
                        "type": "PARAGRAPH",
                        "required": "OPTIONAL"
                      }
                    ]
                 }
                """;
        var response = SurveyIntegrations.surveyCreate(requestBody);
        response
                .statusCode(HttpStatus.OK.value())
                .body("success", equalTo(true))
                .body("data.surveyId", not(emptyString()))
                .body("data.createdAt", notNullValue())
                .body("error", nullValue());
    }

    @Test
    void updateSurvey() {
        SurveyCreateRequest createRequest = testCreateRequest();
        String createRequestBody = toJson(createRequest);
        String surveyId = SurveyIntegrations.surveyCreate(createRequestBody)
                .extract()
                .path("data.surveyId");

        SurveyUpdateRequest updateRequest = SurveyUpdateRequest.builder()
                .id(surveyId)
                .name("Updated Survey 1")
                .description("Updated Description 1")
                .questions(List.of(
                        SurveyQuestionRequest.builder()
                                .question("Updated Question 1")
                                .description("Updated Description 1")
                                .type(SurveyItemType.SINGLE_SELECT)
                                .required(ItemRequired.REQUIRED)
                                .options(List.of(
                                        new SurveyQuestionOptionRequest("Updated Option 1"),
                                        new SurveyQuestionOptionRequest("Updated Option 2")
                                ))
                                .build()
                ))
                .build();

        String updateRequestBody = toJson(updateRequest);
        SurveyIntegrations.surveyUpdate(updateRequestBody)
                .statusCode(HttpStatus.OK.value())
                .body("success", equalTo(true))
                .body("data.surveyId", not(emptyString()))
                .body("data.updatedAt", notNullValue())
                .body("error", nullValue());

    }

    @Test
    void submitSurvey() {
        SurveyCreateRequest createRequest = testCreateRequest();
        String createRequestBody = toJson(createRequest);
        String surveyId = SurveyIntegrations.surveyCreate(createRequestBody)
                .extract()
                .path("data.surveyId");

        String submitRequestBody = """
                    [
                         {
                             "questionId": "1",
                             "answer": "홍길동"
                         },
                         {
                             "questionId": "2",
                             "questionOptionId": "1"
                         },
                         {
                             "questionId": "3",
                             "questionOptionId": "3"
                         },
                         {
                             "questionId": "3",
                             "questionOptionId": "4"
                         },
                         {
                             "questionId": "4",
                             "answer": "아주 좋았습니다."
                         }
                     ]
                """;

        SurveyIntegrations.submitSurveyAnswer(surveyId, submitRequestBody)
                .statusCode(HttpStatus.OK.value())
                .body("success", equalTo(true))
                .body("data", nullValue())
                .body("error", nullValue());
    }

    private String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("json 변환 실패", e);
            throw new RuntimeException(e);
        }
    }

    private SurveyCreateRequest testCreateRequest() {
        return SurveyCreateRequest.builder()
                .name("나만의 설문")
                .description("간단한 설문입니다.")
                .questions(List.of(
                        SurveyQuestionRequest.builder()
                                .question("이름을 알려주세요")
                                .description("당신의 이름은 무엇입니까?")
                                .type(SurveyItemType.TEXT)
                                .required(ItemRequired.REQUIRED)
                                .build(),
                        SurveyQuestionRequest.builder()
                                .question("오늘의 기분을 알려주세요")
                                .description("가벼운 질문부터")
                                .type(SurveyItemType.SINGLE_SELECT)
                                .required(ItemRequired.REQUIRED)
                                .options(List.of(
                                        new SurveyQuestionOptionRequest("좋아요"),
                                        new SurveyQuestionOptionRequest("안좋아요")
                                ))
                                .build(),
                        SurveyQuestionRequest.builder()
                                .question("잠에 드는 시간대가 언제 즈음인가요?")
                                .description("23시에서 01시까지")
                                .type(SurveyItemType.MULTI_SELECT)
                                .required(ItemRequired.REQUIRED)
                                .options(List.of(
                                        new SurveyQuestionOptionRequest("23:00"),
                                        new SurveyQuestionOptionRequest("24:00"),
                                        new SurveyQuestionOptionRequest("01:00")
                                ))
                                .build(),
                        SurveyQuestionRequest.builder()
                                .question("설문이 어떠셨는지 의견을 남겨주세요")
                                .description("간단한 의견이라도 좋습니다. :)")
                                .type(SurveyItemType.PARAGRAPH)
                                .required(ItemRequired.OPTIONAL)
                                .build()
                ))
                .build();
    }

}