package com.practice.survey.response.controller;

import com.practice.survey.common.response.ResponseTemplate;
import com.practice.survey.common.response.StatusEnum;
import com.practice.survey.response.model.dto.ResponseSaveRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Response Management", description = "설문 응답 관리 API")
public interface ResponseControllerDocs {

    @PostMapping("/createResponse")
    @Operation(
            summary = "설문 응답 생성",
            description = "설문에 대한 응답을 생성합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "설문 응답 생성 요청 정보",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseSaveRequestDto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Example 1",
                                            summary = "응답 생성 예시 1",
                                            value = """
                                                    {
                                                      "surveyId": 1,
                                                      "surveyVersionNumber": 1,
                                                      "respondentId": "답변자1",
                                                      "responseItems": [
                                                        {
                                                          "itemId": 1,
                                                          "inputType": "SHORT_TEXT",
                                                          "responseValue": ["SHORT_TEXT_test"]
                                                        },
                                                        {
                                                          "itemId": 2,
                                                          "inputType": "LONG_TEXT",
                                                          "responseValue": ["LOOOOOOOOOOOOOOOOOOOOOOOONG_TEXT_test"]
                                                        },
                                                        {
                                                          "itemId": 3,
                                                          "inputType": "SINGLE_CHOICE",
                                                          "responseValue": ["SINGLE_CHOICE_test1"]
                                                        },
                                                        {
                                                          "itemId": 4,
                                                          "inputType": "LONG_TEXT",
                                                          "responseValue": []
                                                        },
                                                        {
                                                          "itemId": 5,
                                                          "inputType": "MULTIPLE_CHOICE",
                                                          "responseValue": ["MULTIPLE_CHOICE_test1", "MULTIPLE_CHOICE_test2"]
                                                        }
                                                      ]
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "Example 2",
                                            summary = "응답 생성 예시 2",
                                            value = """
                                                    {
                                                      "surveyId": 1,
                                                      "surveyVersionNumber": 2,
                                                      "respondentId": "답변자2",
                                                      "responseItems": [
                                                        {
                                                          "itemId": 6,
                                                          "inputType": "SHORT_TEXT",
                                                          "responseValue": ["SHORT_TEXT_test2"]
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
                            description = "응답 생성 성공",
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
    public ResponseTemplate<StatusEnum> createResponse(@Valid @RequestBody ResponseSaveRequestDto responseSaveRequestDto) ;
}
