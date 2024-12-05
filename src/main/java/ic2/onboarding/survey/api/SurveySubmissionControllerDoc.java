package ic2.onboarding.survey.api;

import ic2.onboarding.survey.dto.AnswerInfo;
import ic2.onboarding.survey.dto.SurveyAnswer;
import ic2.onboarding.survey.global.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "설문조사 응답 API")
public interface SurveySubmissionControllerDoc {

    @Operation(summary = "설문조사 응답 제출",
            description = """
                    설문조사 응답을 제출합니다.
                    등록되어 있는 항목의 정보에 따라서 답변 값을 검증합니다.
                    - path parameter
                      - uuid: 제출할 설문조사 식별자
                    - answers[].questionName: 설문의 설문조사 항목 이름
                    - answers[].singleTextAnswer: MULTIPLE_CHOICE 타입의 항목을 제외한 답변 내용
                    - answers[].multipleTextAnswer: MULTIPLE_CHOICE 타입에 해당하는 답변 목록
                    """,
            requestBody = @RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "요청 예시 1",
                                    value = """
                                            {
                                              "answers": [
                                                {
                                                  "questionName": "이름",
                                                  "singleTextAnswer": "김아무개"
                                                },
                                                {
                                                  "questionName": "나이",
                                                  "singleTextAnswer": "만 27세"
                                                },
                                                {
                                                  "questionName": "자기소개",
                                                  "singleTextAnswer": "안녕하세요."
                                                },
                                                {
                                                  "questionName": "음식1",
                                                  "singleTextAnswer": "제육"
                                                },
                                                {
                                                  "questionName": "음식2",
                                                  "multipleTextAnswer": [
                                                    "명태순살조림",
                                                    "고등어순살조림",
                                                    "동태탕"
                                                  ]
                                                }
                                              ]
                                            }
                                            """
                            )
                    )
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "응답 예시 1",
                                    value = """
                                            {
                                              "code": "SUCCESS",
                                              "message": null,
                                              "validations": null,
                                              "result": {
                                                "submissionId": 1,
                                                "answers": [
                                                  {
                                                    "questionName": "이름",
                                                    "singleTextAnswer": "김아무개",
                                                    "multipleTextAnswer": null
                                                  },
                                                  {
                                                    "questionName": "나이",
                                                    "singleTextAnswer": "만 27세",
                                                    "multipleTextAnswer": null
                                                  },
                                                  {
                                                    "questionName": "자기소개",
                                                    "singleTextAnswer": "안녕하세요.",
                                                    "multipleTextAnswer": null
                                                  },
                                                  {
                                                    "questionName": "음식1",
                                                    "singleTextAnswer": "제육",
                                                    "multipleTextAnswer": null
                                                  },
                                                  {
                                                    "questionName": "음식2",
                                                    "singleTextAnswer": null,
                                                    "multipleTextAnswer": [
                                                      "명태순살조림",
                                                      "고등어순살조림",
                                                      "동태탕"
                                                    ]
                                                  }
                                                ]
                                              }
                                            }
                                            """
                            )
                    )
            )
    )
    ResponseEntity<ApiResult<SurveyAnswer>> submitSurvey(String uuid, SurveyAnswer request);


    @Operation(summary = "설문조사 응답 조회",
            description = """
                    설문조사 응답 목록을 조회합니다.
                    설문조사 항목이 중간에 변경되었더라도 제출 시점의 답변을 반환합니다.
                    - path parameter
                      - uuid: 설문조사 식별자
                    """,
            responses = @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "응답 예시 1",
                                    value = """
                                            
                                            {
                                              "code": "SUCCESS",
                                              "message": null,
                                              "validations": null,
                                              "result": [
                                                {
                                                  "questionName": "이름",
                                                  "singleTextAnswer": "김아무개",
                                                  "multipleTextAnswer": null
                                                },
                                                {
                                                  "questionName": "나이",
                                                  "singleTextAnswer": "만 27세",
                                                  "multipleTextAnswer": null
                                                },
                                                {
                                                  "questionName": "자기소개",
                                                  "singleTextAnswer": "안녕하세요.",
                                                  "multipleTextAnswer": null
                                                },
                                                {
                                                  "questionName": "음식1",
                                                  "singleTextAnswer": "제육",
                                                  "multipleTextAnswer": null
                                                },
                                                {
                                                  "questionName": "음식2",
                                                  "singleTextAnswer": null,
                                                  "multipleTextAnswer": [
                                                    "명태순살조림",
                                                    "고등어순살조림",
                                                    "동태탕"
                                                  ]
                                                }
                                              ]
                                            }
                                            """
                            )
                    )
            )
    )
    ResponseEntity<ApiResult<List<AnswerInfo>>> retrieveSurveySubmissions(String uuid, String name, String answer);

}
