package org.innercircle.surveyapiapplication.domain.survey.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.innercircle.surveyapiapplication.domain.survey.presentation.dto.SurveyCreateRequest;
import org.innercircle.surveyapiapplication.domain.survey.presentation.dto.SurveyInquiryResponse;
import org.innercircle.surveyapiapplication.domain.survey.presentation.dto.SurveyItemAndSubmissionInquiryResponse;
import org.innercircle.surveyapiapplication.domain.surveyItem.presentation.dto.SurveyItemCreateRequest;
import org.innercircle.surveyapiapplication.domain.surveyItem.presentation.dto.SurveyItemInquiryResponse;
import org.innercircle.surveyapiapplication.domain.surveyItem.presentation.dto.SurveyItemUpdateRequest;
import org.innercircle.surveyapiapplication.domain.surveySubmission.presentation.dto.SurveySubmissionCreateRequest;
import org.innercircle.surveyapiapplication.global.handler.response.CustomApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface SurveySwaggerApiDocs {

    @Operation(summary = "Create a new survey", description = "Create a new survey and return its details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Survey created successfully",
            content = @Content(schema = @Schema(implementation = CustomApiResponse.class), examples = {
                @ExampleObject(name = "Success Example", value = """
                    {
                        "code": 1000,
                        "message": "요청 정상 처리되었습니다.",
                        "data": {
                            "id": 1,
                            "name": "Customer Satisfaction Survey",
                            "description": "Survey to evaluate customer satisfaction with our services",
                            "questionResponses": [
                                {
                                    "id": 4091627392079281374,
                                    "version": 1,
                                    "name": "What is your name?",
                                    "description": "Please provide your full name.",
                                    "type": "TEXT",
                                    "required": true,
                                    "surveyId": 1,
                                    "options": []
                                },
                                {
                                    "id": 5548685843904219540,
                                    "version": 1,
                                    "name": "Please describe your experience with our service.",
                                    "description": "Feel free to provide as much detail as you like.",
                                    "type": "PARAGRAPH",
                                    "required": true,
                                    "surveyId": 1,
                                    "options": []
                                },
                                {
                                    "id": 8526088609260093434,
                                    "version": 1,
                                    "name": "How satisfied are you with our service?",
                                    "description": "Choose one of the options below.",
                                    "type": "SINGLE_CHOICE_ANSWER",
                                    "required": true,
                                    "surveyId": 1,
                                    "options": [
                                        "Very Satisfied",
                                        "Satisfied",
                                        "Neutral",
                                        "Dissatisfied",
                                        "Very Dissatisfied"
                                    ]
                                },
                                {
                                    "id": 7686143221321122773,
                                    "version": 1,
                                    "name": "Which of the following features did you use?",
                                    "description": "Select all that apply.",
                                    "type": "MULTI_CHOICE_ANSWER",
                                    "required": false,
                                    "surveyId": 1,
                                    "options": [
                                        "Online Booking",
                                        "Customer Support",
                                        "Mobile App",
                                        "Website"
                                    ]
                                }
                            ]
                        }
                    }
                    """)
            })),
        @ApiResponse(responseCode = "400", description = "Bad request",
            content = @Content(schema = @Schema(implementation = CustomApiResponse.class), examples = {
                @ExampleObject(name = "Error Example", value = """
                             {
                               "code": 2501,
                               "message": "설문항목은 최대 10개까지 가능합니다.",
                               "data": null
                             }
                         """)
            }))
    })
    @PostMapping
    ResponseEntity<CustomApiResponse<SurveyInquiryResponse>> createSurvey(
        @RequestBody SurveyCreateRequest request
    );

    @Operation(summary = "Create a new survey item", description = "Add a new question to the survey")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Survey item created successfully",
            content = @Content(schema = @Schema(implementation = CustomApiResponse.class), examples = {
                @ExampleObject(name = "Success Example", value = """
                             {
                               "code": 1000,
                               "message": "요청 정상 처리되었습니다.",
                               "data":{
                                 "id": 8526088609260093434,
                                 "version": 1,
                                 "name": "How satisfied are you with our service?",
                                 "description": "Choose one of the options below.",
                                 "type": "SINGLE_CHOICE_ANSWER",
                                 "required": true,
                                 "surveyId": 1,
                                 "options": [
                                     "Very Satisfied",
                                     "Satisfied",
                                     "Neutral",
                                     "Dissatisfied",
                                     "Very Dissatisfied"
                                 ]
                               }
                             }
                         """)
            })),
        @ApiResponse(responseCode = "404", description = "Survey not found",
            content = @Content(schema = @Schema(implementation = CustomApiResponse.class), examples = {
                @ExampleObject(name = "Error Example", value = """
                             {
                               "code": 2500,
                               "message": "해당 설문조사는 존재하지 않습니다.",
                               "data": null
                             }
                         """)
            }))
    })
    @PostMapping("/{surveyId}/survey-item")
    ResponseEntity<CustomApiResponse<SurveyItemInquiryResponse>> createSurveyItem(
        @PathVariable(value = "surveyId") Long surveyId,
        @RequestBody SurveyItemCreateRequest request
    );

    @Operation(summary = "Update an existing survey item", description = "Update the question in the survey")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Survey item updated successfully",
            content = @Content(schema = @Schema(implementation = CustomApiResponse.class), examples = {
                @ExampleObject(name = "Success Example", value = """
                             {
                               "code": 1000,
                               "message": "요청 정상 처리되었습니다.",
                               "data":{
                                 "id": 8526088609260093434,
                                 "version": 1,
                                 "name": "How satisfied are you with our service?",
                                 "description": "Choose one of the options below.",
                                 "type": "SINGLE_CHOICE_ANSWER",
                                 "required": true,
                                 "surveyId": 1,
                                 "options": [
                                     "Very Satisfied",
                                     "Satisfied",
                                     "Neutral",
                                     "Dissatisfied",
                                     "Very Dissatisfied"
                                 ]
                               }
                             }
                         """)
            })),
        @ApiResponse(responseCode = "404", description = "Survey item not found",
            content = @Content(schema = @Schema(implementation = CustomApiResponse.class), examples = {
                @ExampleObject(name = "Error Example", value = """
                             {
                               "code": 2600,
                               "message": "해당 설문항목은 존재하지 않습니다.",
                               "data": null
                             }
                         """)
            }))
    })
    @PatchMapping("/{surveyId}/survey-item/{surveyItemId}")
    ResponseEntity<CustomApiResponse<SurveyItemInquiryResponse>> updateSurveyItem(
        @PathVariable(value = "surveyId") Long surveyId,
        @PathVariable(value = "surveyItemId") Long surveyItemId,
        @RequestBody SurveyItemUpdateRequest request
    );

    @Operation(summary = "Create a survey submission", description = "Submit responses to a survey question")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Survey submission created successfully",
            content = @Content(schema = @Schema(implementation = CustomApiResponse.class), examples = {
                @ExampleObject(name = "Success Example", value = """
                             {
                               "code": 1000,
                               "message": "요청 정상 처리되었습니다.",
                               "data": null
                             }
                         """)
            })),
        @ApiResponse(responseCode = "404", description = "Survey item not found",
            content = @Content(schema = @Schema(implementation = CustomApiResponse.class), examples = {
                @ExampleObject(name = "Error Example", value = """
                             {
                               "code": 2600,
                               "message": "해당 설문항목은 존재하지 않습니다.",
                               "data": null
                             }
                         """)
            }))
    })
    @PostMapping("/{surveyId}/survey-item/{surveyItemId}/{surveyItemVersion}/survey-submission")
    ResponseEntity<CustomApiResponse<Void>> createSurveySubmission(
        @PathVariable(value = "surveyId") Long surveyId,
        @PathVariable(value = "surveyItemId") Long surveyItemId,
        @PathVariable(value = "surveyItemVersion") int surveyItemVersion,
        @RequestBody SurveySubmissionCreateRequest request
    );

    @Operation(summary = "Get all survey items and submissions", description = "Retrieve all survey items and their associated submissions")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Survey items and submissions retrieved successfully",
            content = @Content(schema = @Schema(implementation = CustomApiResponse.class), examples = {
                @ExampleObject(name = "Success Example", value = """
                        {
                            "code": 1000,
                            "message": "요청 정상 처리되었습니다.",
                            "data": [
                                {
                                    "surveyItemId": 4478776048616913158,
                                    "surveyItemVersion": 1,
                                    "surveyItemName": "What is your name?",
                                    "surveyItemDescription": "Please provide your full name.",
                                    "type": "TEXT",
                                    "required": true,
                                    "submissionInquiryResponses": []
                                },
                                {
                                    "surveyItemId": 2382560373560986595,
                                    "surveyItemVersion": 1,
                                    "surveyItemName": "Please describe your experience with our service.",
                                    "surveyItemDescription": "Feel free to provide as much detail as you like.",
                                    "type": "PARAGRAPH",
                                    "required": true,
                                    "submissionInquiryResponses": []
                                },
                                {
                                    "surveyItemId": 3906812548758061624,
                                    "surveyItemVersion": 1,
                                    "surveyItemName": "How satisfied are you with our service?",
                                    "surveyItemDescription": "Choose one of the options below.",
                                    "type": "SINGLE_CHOICE_ANSWER",
                                    "required": true,
                                    "submissionInquiryResponses": []
                                },
                                {
                                    "surveyItemId": 7327894867729559762,
                                    "surveyItemVersion": 1,
                                    "surveyItemName": "Which of the following features did you use?",
                                    "surveyItemDescription": "Select all that apply.",
                                    "type": "MULTI_CHOICE_ANSWER",
                                    "required": false,
                                    "submissionInquiryResponses": [
                                        {
                                            "surveySubmissionId": 1,
                                            "surveyItemId": 7327894867729559762,
                                            "surveyItemVersion": 1,
                                            "response": [
                                                "Online Booking"
                                            ]
                                        }
                                    ]
                                }
                            ]
                        }
                        """)
                })),
        @ApiResponse(responseCode = "404", description = "Survey not found",
            content = @Content(schema = @Schema(implementation = CustomApiResponse.class), examples = {
                @ExampleObject(name = "Error Example", value = """
                             {
                               "code": 2500,
                               "message": "해당 설문조사는 존재하지 않습니다.",
                               "data": null
                             }
                         """)
            }))
    })
    @GetMapping("/{surveyId}")
    ResponseEntity<CustomApiResponse<List<SurveyItemAndSubmissionInquiryResponse>>> getAllSurveyItemAndSubmissionOfSurvey(
        @PathVariable(value = "surveyId") Long surveyId
    );

}
