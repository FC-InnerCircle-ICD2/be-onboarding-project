package net.gentledot.survey.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import lombok.extern.slf4j.Slf4j;
import net.gentledot.survey.config.IntegrationTestDatabaseClearing;
import net.gentledot.survey.dto.enums.UpdateType;
import net.gentledot.survey.dto.request.SurveyCreateRequest;
import net.gentledot.survey.dto.request.SurveyQuestionOptionRequest;
import net.gentledot.survey.dto.request.SurveyQuestionRequest;
import net.gentledot.survey.dto.request.SurveyUpdateRequest;
import net.gentledot.survey.model.entity.surveybase.SurveyQuestion;
import net.gentledot.survey.model.enums.ItemRequired;
import net.gentledot.survey.model.enums.SurveyItemType;
import net.gentledot.survey.repository.SurveyQuestionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(IntegrationTestDatabaseClearing.class)
class SurveyIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    IntegrationTestDatabaseClearing integrationTestDatabaseClearing;

    @Autowired
    SurveyQuestionRepository surveyQuestionRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @AfterEach
    void tearDown() {
        integrationTestDatabaseClearing.clearAllH2Database();
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
                        "required": "REQUIRED",
                        "options": [
                            {"option" : "입력 1"}
                        ]
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
                        "required": "OPTIONAL",
                        "options": [
                            {"option" : "입력 1"}
                        ]
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
        ExtractableResponse<Response> extract = SurveyIntegrations.surveyCreate(createRequestBody)
                .extract();
        String surveyId = extract.path("data.surveyId");
        Integer questionId = extract.path("data.questions[0].questionId");
        Integer questionId2 = extract.path("data.questions[1].questionId");

        SurveyUpdateRequest updateRequest = SurveyUpdateRequest.builder()
                .id(surveyId)
                .name("Updated Survey 1")
                .description("Updated Description 1")
                .questions(List.of(
                        SurveyQuestionRequest.builder()
                                .questionId(Long.valueOf(questionId))
                                .updateType(UpdateType.MODIFY)
                                .question("Updated Question 1")
                                .description("Updated Description 1")
                                .type(SurveyItemType.SINGLE_SELECT)
                                .required(ItemRequired.REQUIRED)
                                .options(List.of(
                                        new SurveyQuestionOptionRequest("Updated Option 1"),
                                        new SurveyQuestionOptionRequest("Updated Option 2")
                                ))
                                .build(),
                        SurveyQuestionRequest.builder()
                                .questionId(Long.valueOf(questionId2))
                                .updateType(UpdateType.DELETE)
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

        Optional<SurveyQuestion> byId = surveyQuestionRepository.findById(Long.valueOf(questionId2));
        Assertions.assertThat(byId).isEmpty();

    }

    @Test
    void submitSurvey() {
        SurveyCreateRequest createRequest = testCreateRequest();
        String createRequestBody = toJson(createRequest);
        String surveyId = SurveyIntegrations.surveyCreate(createRequestBody)
                .extract()
                .path("data.surveyId");

        String submitRequestBody = testSurveyAnswerRequestBody();

        ValidatableResponse validatableResponse = SurveyIntegrations.submitSurveyAnswer(surveyId, submitRequestBody);
        validatableResponse
                .statusCode(HttpStatus.OK.value())
                .body("success", equalTo(true))
                .body("data", nullValue())
                .body("error", nullValue());
    }

    private static String testSurveyAnswerRequestBody() {
        return """
                [
                    {
                        "questionId": 1,
                        "answer": ["홍길동"]
                    },
                    {
                        "questionId": 2,
                        "answer": ["좋아요"]
                    },
                    {
                        "questionId": 3,
                        "answer": ["23:00", "24:00"]
                    },
                    {
                        "questionId": 4,
                        "answer": ["아주 좋았습니다."]
                    }
                ]
                """;
    }

    @Test
    void getAllSurveyAnswer() {
        SurveyCreateRequest createRequest = testCreateRequest();
        String createRequestBody = toJson(createRequest);
        String surveyId = SurveyIntegrations.surveyCreate(createRequestBody)
                .extract()
                .path("data.surveyId");

        String submitRequestBody = testSurveyAnswerRequestBody();
        SurveyIntegrations.submitSurveyAnswer(surveyId, submitRequestBody);

        SurveyIntegrations.getAllSurveyAnswers(surveyId, null, null)
                .statusCode(HttpStatus.OK.value())
                .body("success", equalTo(true))
                .body("data.surveyId", equalTo(surveyId))
                .body("data.answerList", hasSize(1))
                .body("data.answerList[0].answerId", notNullValue())
                .body("data.answerList[0].answers", hasSize(4))
                .body("data.answerList[0].answers[0]", hasKey("questionName"))
                .body("data.answerList[0].answers[0]", hasKey("answerValue"))
                .body("data.answerList[0].answers[1]", hasKey("questionName"))
                .body("data.answerList[0].answers[1]", hasKey("answerValue"))
                .body("data.answerList[0].answers[2]", hasKey("questionName"))
                .body("data.answerList[0].answers[2]", hasKey("answerValue"))
                .body("data.answerList[0].answers[3]", hasKey("questionName"))
                .body("data.answerList[0].answers[3]", hasKey("answerValue"))
                .body("error", nullValue());
    }

    @DisplayName("설문 생성 - 설문 답변 제출 - 설문 수정 - 설문 답변 (2) - 설문 전체 조회를 쭉보자.")
    @Test
    void fullSurveyLifecycleTest() {
        // Step 1: 서베이 생성
        SurveyCreateRequest createRequest = testCreateRequestSimpleVersion();
        String createRequestBody = toJson(createRequest);
        ExtractableResponse<Response> createResponse = SurveyIntegrations.surveyCreate(createRequestBody)
                .extract();
        String surveyId = createResponse.path("data.surveyId");
        Integer questionId1 = createResponse.path("data.questions[0].questionId");
        Integer questionId2 = createResponse.path("data.questions[1].questionId");

        // Step 2: 최초 응답 받기
        String initialSubmitRequestBody = """
                [
                    {
                        "questionId": %d,
                        "answer": ["홍길동"]
                    },
                    {
                        "questionId": %d,
                        "answer": ["좋아요"]
                    }
                ]
                """.formatted(questionId1, questionId2);

        SurveyIntegrations.submitSurveyAnswer(surveyId, initialSubmitRequestBody)
                .statusCode(HttpStatus.OK.value())
                .body("success", equalTo(true))
                .body("data", nullValue())
                .body("error", nullValue());

        // Step 3: 응답 수정 처리
        SurveyUpdateRequest updateRequest = SurveyUpdateRequest.builder()
                .id(surveyId)
                .name("Updated Survey")
                .description("Updated Description")
                .questions(List.of(
                        SurveyQuestionRequest.builder()
                                .questionId(Long.valueOf(questionId1))
                                .updateType(UpdateType.MODIFY)
                                .question("Updated Question 1")
                                .description("Updated Description 1")
                                .type(SurveyItemType.SINGLE_SELECT)
                                .required(ItemRequired.REQUIRED)
                                .options(List.of(
                                        new SurveyQuestionOptionRequest("Updated Option 1"),
                                        new SurveyQuestionOptionRequest("Updated Option 2")
                                ))
                                .build(),
                        SurveyQuestionRequest.builder()
                                .questionId(Long.valueOf(questionId2))
                                .updateType(UpdateType.DELETE)
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

        // Step 4: 응답 추가 조회
        String updatedSubmitRequestBody = """
                [
                    {
                        "questionId": %d,
                        "answer": ["Updated Option 1"]
                    }
                ]
                """.formatted(questionId1);

        SurveyIntegrations.submitSurveyAnswer(surveyId, updatedSubmitRequestBody)
                .statusCode(HttpStatus.OK.value())
                .body("success", equalTo(true))
                .body("data", nullValue())
                .body("error", nullValue());

        // Step 5: 전체 응답 조회 하기
        SurveyIntegrations.getAllSurveyAnswers(surveyId, null, null)
                .statusCode(HttpStatus.OK.value())
                .body("success", equalTo(true))
                .body("data.surveyId", equalTo(surveyId))
                .body("data.answerList", hasSize(2))
                .body("data.answerList[0].answers", hasSize(2))
                .body("data.answerList[1].answers", hasSize(1))
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
                                .options(List.of(
                                        new SurveyQuestionOptionRequest("입력 1")
                                ))
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
                                .options(List.of(
                                        new SurveyQuestionOptionRequest("입력 2")
                                ))
                                .build()
                ))
                .build();
    }

    private SurveyCreateRequest testCreateRequestSimpleVersion() {
        return SurveyCreateRequest.builder()
                .name("나만의 설문")
                .description("간단한 설문입니다.")
                .questions(List.of(
                        SurveyQuestionRequest.builder()
                                .question("이름을 알려주세요")
                                .description("당신의 이름은 무엇입니까?")
                                .type(SurveyItemType.TEXT)
                                .required(ItemRequired.REQUIRED)
                                .options(List.of(
                                        new SurveyQuestionOptionRequest("입력 1")
                                ))
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
                                .build()
                ))
                .build();
    }


}