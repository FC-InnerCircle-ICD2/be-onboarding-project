package com.practice.survey.surveymngt.controller;

import com.practice.survey.common.response.ResponseTemplate;
import com.practice.survey.common.response.StatusEnum;
import com.practice.survey.surveymngt.model.dto.SurveyRequestDto;
import com.practice.survey.surveymngt.model.dto.SurveyResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Survey Management", description = "설문조사 관리 API")
public interface SurveyControllerDocs {

    @PostMapping("/createSurvey")
    @Operation(
            summary = "설문조사 생성",
            description = "설문조사를 생성합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "설문조사 생성 요청 정보",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SurveyRequestDto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Example 1",
                                            summary = "설문조사 생성 예시 1",
                                            value = """
                                                    {
                                                      "name": "설문조사1",
                                                      "description": "설문조사1 설명",
                                                      "surveyItems": [
                                                        {
                                                          "itemNumber": 1,
                                                          "name": "질문1",
                                                          "description": "설명1",
                                                          "inputType": "SINGLE_CHOICE",
                                                          "required": true,
                                                          "options": [
                                                            {
                                                              "optionNumber": 1,
                                                              "optionText": "선택지1"
                                                            },
                                                            {
                                                              "optionNumber": 2,
                                                              "optionText": "선택지2"
                                                            }
                                                          ]
                                                        },
                                                        {
                                                          "itemNumber": 2,
                                                          "name": "질문2",
                                                          "description": "설명2",
                                                          "inputType": "LONG_TEXT",
                                                          "required": true
                                                        }
                                                      ]
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "Example 2",
                                            summary = "설문조사 생성 예시 2",
                                            value = """
                                                    {
                                                      "name": "설문조사1",
                                                      "description": "설문조사1 설명",
                                                      "surveyItems": [
                                                        {
                                                          "itemNumber": 1,
                                                          "name": "질문1",
                                                          "description": "설명1",
                                                          "inputType": "SHORT_TEXT",
                                                          "required": true
                                                        },
                                                        {
                                                          "itemNumber": 2,
                                                          "name": "질문2",
                                                          "description": "설명2",
                                                          "inputType": "LONG_TEXT",
                                                          "required": true
                                                        },
                                                        {
                                                          "itemNumber": 3,
                                                          "name": "질문3",
                                                          "description": "설명3",
                                                          "inputType": "SINGLE_CHOICE",
                                                          "required": true,
                                                          "options": [
                                                            {
                                                              "optionNumber": 1,
                                                              "optionText": "SINGLE_CHOICE_test1"
                                                            },
                                                            {
                                                              "optionNumber": 2,
                                                              "optionText": "SINGLE_CHOICE_test2"
                                                            }
                                                          ]
                                                        },
                                                        {
                                                          "itemNumber": 4,
                                                          "name": "질문4",
                                                          "description": "설명4",
                                                          "inputType": "LONG_TEXT",
                                                          "required": false
                                                        },
                                                        {
                                                          "itemNumber": 5,
                                                          "name": "질문5",
                                                          "description": "설명5",
                                                          "inputType": "MULTIPLE_CHOICE",
                                                          "required": true,
                                                          "options": [
                                                            {
                                                              "optionNumber": 1,
                                                              "optionText": "MULTIPLE_CHOICE_test1"
                                                            },
                                                            {
                                                              "optionNumber": 2,
                                                              "optionText": "MULTIPLE_CHOICE_test2"
                                                            },
                                                            {
                                                              "optionNumber": 3,
                                                              "optionText": "MULTIPLE_CHOICE_test3"
                                                            }
                                                          ]
                                                        }
                                                      ]
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "설문조사 생성 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = StatusEnum.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                        "common": {
                                                            "code": 200,
                                                            "message": "Success"
                                                        },
                                                        "data": {
                                                            "value": "SUCCESS"
                                                        }
                                                    }
                                                    """
                                    )
                            )
                    )
            }
    )
    public ResponseTemplate<StatusEnum> createSurvey(@Valid @RequestBody SurveyRequestDto surveyRequestDto) ;

    @PutMapping("/updateSurvey")
    @Operation(
            summary = "설문조사 수정",
            description = "설문조사를 수정합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "설문조사 수정 요청 정보",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SurveyRequestDto.class),
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                      "name": "설문조사1",
                                                      "description": "설문조사1 수정",
                                                      "surveyItems": [
                                                        {
                                                          "itemNumber": 1,
                                                          "name": "수정질문1",
                                                          "description": "수정설명1",
                                                          "inputType": "SHORT_TEXT",
                                                          "required": false
                                                        }
                                                      ]
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "설문조사 수정 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = StatusEnum.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                        "common": {
                                                            "code": 200,
                                                            "message": "Success"
                                                        },
                                                        "data": {
                                                            "value": "SUCCESS"
                                                        }
                                                    }
                                                    """
                                    )
                            )
                    )
            }
    )
    public ResponseTemplate<StatusEnum> updateSurvey(@Valid @RequestBody SurveyRequestDto surveyRequestDto) ;

    @GetMapping("/getSurveyResponse")
    @Operation(
            summary = "설문조사 응답 조회",
            description = """
                지정된 설문조사 ID, 항목 이름, 응답 값을 사용하여 설문조사 응답을 조회합니다.
                """,
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "설문조사 응답 조회 성공",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = SurveyResponseDto.class),
                                            examples = {
                                                    @ExampleObject(
                                                            name = "응답 예시 1",
                                                            summary = "surveyId만 포함된 요청의 응답 예시",
                                                            value = """
                                                                {
                                                                    "common": {
                                                                        "code": 200,
                                                                        "message": "Success"
                                                                    },
                                                                    "data": {
                                                                        "value": [
                                                                            {
                                                                                "surveyName": "설문조사1",
                                                                                "surveyDescription": "설문조사1 설명",
                                                                                "versionNumber": 1,
                                                                                "itemName": "질문1",
                                                                                "itemDescription": "설명1",
                                                                                "itemNumber": 1,
                                                                                "respondentId": "답변자1",
                                                                                "responseValue": "SHORT_TEXT_test"
                                                                            }
                                                                        ]
                                                                    }
                                                                }
                                                                """
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    public ResponseTemplate<List<SurveyResponseDto>> getSurveyResponse(
            @Parameter(description = "설문조사 ID", required = true, example = "1")
            @RequestParam(required = true) Long surveyId,

            @Parameter(description = "설문조사 항목 이름", required = false, example = "질문1")
            @RequestParam(required = false) String itemName,

            @Parameter(description = "응답 값", required = false, example = "TEXT")
            @RequestParam(required = false) String responseValue);
}
