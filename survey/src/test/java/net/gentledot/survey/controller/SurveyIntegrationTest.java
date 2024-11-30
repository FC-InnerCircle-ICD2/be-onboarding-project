package net.gentledot.survey.controller;

import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

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
}