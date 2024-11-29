package ic2.onboarding.survey.api;

import ic2.onboarding.survey.dto.SurveySubmissionRequest;
import ic2.onboarding.survey.dto.SurveySubmissionResponse;
import ic2.onboarding.survey.global.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Tag(name = "설문조사 응답 제출 API")
public interface SurveySubmissionControllerDoc {

    @Operation(summary = "설문조사 응답 제출",
            description = "설문조사 응답을 제출합니다.",
            requestBody = @RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "요청 예시",
                                    value = """
                                            {
                                              "items": [
                                                {
                                                  "itemId": 1,
                                                  "answer": "1 답변"
                                                },
                                                {
                                                  "itemId": 2,
                                                  "answer": "2 답변"
                                                },
                                                {
                                                  "itemId": 3,
                                                  "answer": "피자"
                                                },
                                                {
                                                  "itemId": 4,
                                                  "answer": "고등어순살조림"
                                                },
                                                {
                                                  "itemId": 4,
                                                  "answer": "명태순살조림"
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
                                    name = "응답 예시",
                                    value = """
                                            {
                                              "code": "SUCCESS",
                                              "message": null,
                                              "validations": null,
                                              "result": {
                                                "items": [
                                                  {
                                                    "id": 1,
                                                    "itemId": 1,
                                                    "name": "이름",
                                                    "inputType": "SHORT_ANSWER",
                                                    "answer": "1 답변"
                                                  },
                                                  {
                                                    "id": 2,
                                                    "itemId": 2,
                                                    "name": "나이",
                                                    "inputType": "SHORT_ANSWER",
                                                    "answer": "2 답변"
                                                  },
                                                  {
                                                    "id": 3,
                                                    "itemId": 3,
                                                    "name": "음식선택",
                                                    "inputType": "SINGLE_CHOICE",
                                                    "answer": "피자"
                                                  },
                                                  {
                                                    "id": 4,
                                                    "itemId": 4,
                                                    "name": "음식제외",
                                                    "inputType": "MULTIPLE_CHOICE",
                                                    "answer": "고등어순살조림"
                                                  },
                                                  {
                                                    "id": 5,
                                                    "itemId": 4,
                                                    "name": "음식제외",
                                                    "inputType": "MULTIPLE_CHOICE",
                                                    "answer": "명태순살조림"
                                                  }
                                                ]
                                              }
                                            }
                                            """
                            )
                    )
            )
    )
    ResponseEntity<ApiResult<SurveySubmissionResponse>> submitSurvey(Long id, SurveySubmissionRequest request);


    // TODO 구현
    @Operation(summary = "설문조사 응답 조회",
            description = "설문조사 응답을 조회합니다.",
            responses = @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "응답 예시",
                                    value = """
                                            {
                                              "code": "SUCCESS",
                                              "message": null,
                                              "validations": null,
                                              "result": {
                                                "items": [
                                                  {
                                                    "id": 1,
                                                    "itemId": 1,
                                                    "name": "이름",
                                                    "inputType": "SHORT_ANSWER",
                                                    "answer": "1 답변"
                                                  },
                                                  {
                                                    "id": 2,
                                                    "itemId": 2,
                                                    "name": "나이",
                                                    "inputType": "SHORT_ANSWER",
                                                    "answer": "2 답변"
                                                  },
                                                  {
                                                    "id": 3,
                                                    "itemId": 3,
                                                    "name": "음식선택",
                                                    "inputType": "SINGLE_CHOICE",
                                                    "answer": "피자"
                                                  },
                                                  {
                                                    "id": 4,
                                                    "itemId": 4,
                                                    "name": "음식제외",
                                                    "inputType": "MULTIPLE_CHOICE",
                                                    "answer": "고등어순살조림"
                                                  },
                                                  {
                                                    "id": 5,
                                                    "itemId": 4,
                                                    "name": "음식제외",
                                                    "inputType": "MULTIPLE_CHOICE",
                                                    "answer": "명태순살조림"
                                                  }
                                                ]
                                              }
                                            }
                                            """
                            )
                    )
            )
    )
    ResponseEntity<ApiResult<SurveySubmissionResponse>> retrieveSurveySubmissions(Long id);

}
