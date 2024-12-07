package ic2.onboarding.survey.api;

import ic2.onboarding.survey.dto.SurveyCreation;
import ic2.onboarding.survey.dto.SurveyInfo;
import ic2.onboarding.survey.global.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Tag(name = "설문조사 API")
public interface SurveyControllerDoc {


    @Operation(summary = "설문조사 생성",
            description = """
                    설문조사를 생성합니다.
                    - info.basic.name: 설문조사 이름
                    - info.basic.description: 설문조사 설명
                    - info.items[].name: 항목 이름
                    - info.items[].description: 항목 설명
                    - info.items[].inputType: 항목 타입 (SHORT_ANSWER | LONG_ANSWER | SINGLE_CHOICE | MULTIPLE_CHOICE 중 하나)
                    - info.items[].required: 필수여부
                    - info.items[].choiceOptions[]: 선택형 항목 목록 배열 (타입이 SINGLE_CHOICE 또는 MULTIPLE_CHOICE 일 때)
                    """,
            requestBody = @RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "요청 예시 1",
                                    value = """
                                            {
                                              "info": {
                                                "basic": {
                                                  "name": "설문조사 1",
                                                  "description": "설문조사 입니다."
                                                },
                                                "questions": [
                                                  {
                                                    "name": "이름",
                                                    "description": "이름을 알려주세요.",
                                                    "inputType": "SHORT_ANSWER",
                                                    "required": true,
                                                    "choiceOptions": []
                                                  },
                                                  {
                                                    "name": "나이",
                                                    "description": "나이도 알려주세요.",
                                                    "inputType": "SHORT_ANSWER",
                                                    "required": false,
                                                    "choiceOptions": []
                                                  },
                                                  {
                                                    "name": "자기소개",
                                                    "description": "자기소개 부탁드립니다.",
                                                    "inputType": "LONG_ANSWER",
                                                    "required": true,
                                                    "choiceOptions": []
                                                  },
                                                  {
                                                    "name": "음식1",
                                                    "description": "좋아하는 음식을 1개 골라주세요.",
                                                    "inputType": "SINGLE_CHOICE",
                                                    "required": true,
                                                    "choiceOptions": [
                                                      "제육",
                                                      "돈까스",
                                                      "치킨",
                                                      "피자",
                                                      "떡볶이"
                                                    ]
                                                  },
                                                  {
                                                    "name": "음식2",
                                                    "description": "싫어하는 음식을 모두 골라주세요.",
                                                    "inputType": "MULTIPLE_CHOICE",
                                                    "required": false,
                                                    "choiceOptions": [
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
                                                "uuid": "aaffafda-884e-4d86-bb4b-ecef3da7544e",
                                                "info": {
                                                  "basic": {
                                                    "name": "설문조사 1",
                                                    "description": "설문조사 입니다."
                                                  },
                                                  "questions": [
                                                    {
                                                      "name": "이름",
                                                      "description": "이름을 알려주세요.",
                                                      "inputType": "SHORT_ANSWER",
                                                      "required": true,
                                                      "choiceOptions": []
                                                    },
                                                    {
                                                      "name": "나이",
                                                      "description": "나이도 알려주세요.",
                                                      "inputType": "SHORT_ANSWER",
                                                      "required": false,
                                                      "choiceOptions": []
                                                    },
                                                    {
                                                      "name": "자기소개",
                                                      "description": "자기소개 부탁드립니다.",
                                                      "inputType": "LONG_ANSWER",
                                                      "required": true,
                                                      "choiceOptions": []
                                                    },
                                                    {
                                                      "name": "음식1",
                                                      "description": "좋아하는 음식을 1개 골라주세요.",
                                                      "inputType": "SINGLE_CHOICE",
                                                      "required": true,
                                                      "choiceOptions": [
                                                        "제육",
                                                        "돈까스",
                                                        "치킨",
                                                        "피자",
                                                        "떡볶이"
                                                      ]
                                                    },
                                                    {
                                                      "name": "음식2",
                                                      "description": "싫어하는 음식을 모두 골라주세요.",
                                                      "inputType": "MULTIPLE_CHOICE",
                                                      "required": false,
                                                      "choiceOptions": [
                                                        "명태순살조림",
                                                        "고등어순살조림",
                                                        "동태탕"
                                                      ]
                                                    }
                                                  ]
                                                }
                                              }
                                            }
                                            """
                            )
                    )
            )
    )
    ResponseEntity<ApiResult<SurveyCreation>> createSurvey(SurveyCreation request);


    @Operation(summary = "설문조사 수정",
            description = """
                    설문조사를 수정합니다.
                    - path parameter
                      - uuid: 수정할 설문조사 식별값 ex) efad2a93-f5c4-4816-bc7c-6dc22e9b6c75
                    - basic.name: 설문조사 이름
                    - basic.description: 설문조사 설명
                    - items[]
                      - <b>기존 항목이 요청 목록에 없다면 삭제됩니다.</b>
                    - items[].id: 항목 식별자 (존재시 기존 항목 변경, 미존재시 신규 항목 등록)
                    - items[].name: 항목 이름
                    - items[].description: 항목 설명
                    - items[].inputType: 항목 타입 (SHORT_ANSWER | LONG_ANSWER | SINGLE_CHOICE | MULTIPLE_CHOICE 중 하나)
                    - items[].required: 필수여부
                    - items[].choiceOptions[]: 선택형 항목 목록 배열 (타입이 SINGLE_CHOICE 또는 MULTIPLE_CHOICE 일 때)
                    """,
            requestBody = @RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "요청 예시 1",
                                    value = """
                                            {
                                              "basic": {
                                                "name": "설문조사 1",
                                                "description": "설문조사 입니다."
                                              },
                                              "questions": [
                                                {
                                                  "name": "이름",
                                                  "description": "이름을 알려주세요.",
                                                  "inputType": "SHORT_ANSWER",
                                                  "required": true,
                                                  "choiceOptions": []
                                                },
                                                {
                                                  "name": "나이",
                                                  "description": "나이도 알려주세요.",
                                                  "inputType": "SHORT_ANSWER",
                                                  "required": false,
                                                  "choiceOptions": []
                                                },
                                                {
                                                  "name": "자기소개",
                                                  "description": "자기소개 부탁드립니다.",
                                                  "inputType": "LONG_ANSWER",
                                                  "required": true,
                                                  "choiceOptions": []
                                                },
                                                {
                                                  "name": "음식1",
                                                  "description": "좋아하는 음식을 1개 골라주세요.",
                                                  "inputType": "SINGLE_CHOICE",
                                                  "required": true,
                                                  "choiceOptions": [
                                                    "제육",
                                                    "돈까스",
                                                    "치킨",
                                                    "피자",
                                                    "떡볶이"
                                                  ]
                                                },
                                                {
                                                  "name": "음식2",
                                                  "description": "싫어하는 음식을 모두 골라주세요.",
                                                  "inputType": "MULTIPLE_CHOICE",
                                                  "required": false,
                                                  "choiceOptions": [
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
                                              "basic": {
                                                "name": "설문조사 1",
                                                "description": "설문조사 입니다."
                                              },
                                              "questions": [
                                                {
                                                  "name": "이름",
                                                  "description": "이름을 알려주세요.",
                                                  "inputType": "SHORT_ANSWER",
                                                  "required": true,
                                                  "choiceOptions": []
                                                },
                                                {
                                                  "name": "나이",
                                                  "description": "나이도 알려주세요.",
                                                  "inputType": "SHORT_ANSWER",
                                                  "required": false,
                                                  "choiceOptions": []
                                                },
                                                {
                                                  "name": "자기소개",
                                                  "description": "자기소개 부탁드립니다.",
                                                  "inputType": "LONG_ANSWER",
                                                  "required": true,
                                                  "choiceOptions": []
                                                },
                                                {
                                                  "name": "음식1",
                                                  "description": "좋아하는 음식을 1개 골라주세요.",
                                                  "inputType": "SINGLE_CHOICE",
                                                  "required": true,
                                                  "choiceOptions": [
                                                    "제육",
                                                    "돈까스",
                                                    "치킨",
                                                    "피자",
                                                    "떡볶이"
                                                  ]
                                                },
                                                {
                                                  "name": "음식2",
                                                  "description": "싫어하는 음식을 모두 골라주세요.",
                                                  "inputType": "MULTIPLE_CHOICE",
                                                  "required": false,
                                                  "choiceOptions": [
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
    ResponseEntity<ApiResult<SurveyInfo>> updateSurvey(String uuid, SurveyInfo request);

}
