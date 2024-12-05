package org.innercircle.surveyapiapplication.domain.survey.presentation;

import lombok.RequiredArgsConstructor;
import org.innercircle.surveyapiapplication.domain.survey.application.SurveyService;
import org.innercircle.surveyapiapplication.domain.survey.domain.Survey;
import org.innercircle.surveyapiapplication.domain.survey.presentation.dto.SurveyCreateRequest;
import org.innercircle.surveyapiapplication.domain.survey.presentation.dto.SurveyInquiryResponse;
import org.innercircle.surveyapiapplication.domain.surveyItem.application.SurveyItemService;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.SurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.presentation.dto.SurveyItemCreateRequest;
import org.innercircle.surveyapiapplication.domain.surveyItem.presentation.dto.SurveyItemInquiryResponse;
import org.innercircle.surveyapiapplication.domain.surveyItem.presentation.dto.SurveyItemUpdateRequest;
import org.innercircle.surveyapiapplication.global.handler.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/survey")
@RequiredArgsConstructor
public class SurveyApiController {

    private final SurveyService surveyService;
    private final SurveyItemService surveyItemService;

    @GetMapping("/{surveyId}")
    public ResponseEntity<ApiResponse<SurveyInquiryResponse>> getSurvey(
        @PathVariable(value = "surveyId") Long surveyId
    ) {
        Survey survey = surveyService.findById(surveyId);
        SurveyInquiryResponse response = SurveyInquiryResponse.from(survey);
        return ResponseEntity.ok()
            .body(ApiResponse.onSuccess(response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SurveyInquiryResponse>> createSurvey(
        @RequestBody SurveyCreateRequest request
    ) {
       Survey survey = request.toDomain();
       SurveyInquiryResponse response = SurveyInquiryResponse.from(surveyService.createSurvey(survey));
       return ResponseEntity.ok()
           .body(ApiResponse.onSuccess(response));
    }

    @PostMapping("/{surveyId}/survey-item")
    public ResponseEntity<ApiResponse<SurveyItemInquiryResponse>> createSurveyItem(
        @PathVariable(value = "surveyId") Long surveyId,
        @RequestBody SurveyItemCreateRequest request
    ) {
        SurveyItem surveyItem = request.toDomain();
        SurveyItemInquiryResponse response = SurveyItemInquiryResponse.from(surveyItemService.createQuestion(surveyId, surveyItem));
        return ResponseEntity.ok()
            .body(ApiResponse.onSuccess(response));
    }

    @PatchMapping("/{surveyId}/survey-item/{surveyItemId}")
    public ResponseEntity<ApiResponse<SurveyItemInquiryResponse>> updateSurveyItem(
        @PathVariable(value = "surveyId") Long surveyId,
        @PathVariable(value = "surveyItemId") Long surveyItemId,
        @RequestBody SurveyItemUpdateRequest request
    ) {
        SurveyItem surveyItem = surveyItemService.updateQuestion(surveyId, surveyItemId, request);
        SurveyItemInquiryResponse response = SurveyItemInquiryResponse.from(surveyItem);
        return ResponseEntity.ok()
            .body(ApiResponse.onSuccess(response));
    }

}
