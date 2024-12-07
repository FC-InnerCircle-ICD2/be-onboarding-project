package org.innercircle.surveyapiapplication.domain.survey.presentation;

import lombok.RequiredArgsConstructor;
import org.innercircle.surveyapiapplication.domain.survey.application.SurveyService;
import org.innercircle.surveyapiapplication.domain.survey.domain.Survey;
import org.innercircle.surveyapiapplication.domain.survey.presentation.dto.SurveyCreateRequest;
import org.innercircle.surveyapiapplication.domain.survey.presentation.dto.SurveyInquiryResponse;
import org.innercircle.surveyapiapplication.domain.survey.presentation.dto.SurveyItemAndSubmissionInquiryResponse;
import org.innercircle.surveyapiapplication.domain.surveyItem.application.SurveyItemService;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.SurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.presentation.dto.SurveyItemCreateRequest;
import org.innercircle.surveyapiapplication.domain.surveyItem.presentation.dto.SurveyItemInquiryResponse;
import org.innercircle.surveyapiapplication.domain.surveyItem.presentation.dto.SurveyItemUpdateRequest;
import org.innercircle.surveyapiapplication.domain.surveySubmission.application.SurveySubmissionService;
import org.innercircle.surveyapiapplication.domain.surveySubmission.presentation.dto.SurveySubmissionCreateRequest;
import org.innercircle.surveyapiapplication.global.handler.response.CustomApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/survey")
@RequiredArgsConstructor
public class SurveyApiController implements SurveySwaggerApiDocs {

    private final SurveyService surveyService;
    private final SurveyItemService surveyItemService;
    private final SurveySubmissionService surveySubmissionService;

    @PostMapping
    public ResponseEntity<CustomApiResponse<SurveyInquiryResponse>> createSurvey(
        @RequestBody SurveyCreateRequest request
    ) {
       Survey survey = request.toDomain();
       SurveyInquiryResponse response = SurveyInquiryResponse.from(surveyService.createSurvey(survey));
       return ResponseEntity.ok()
           .body(CustomApiResponse.onSuccess(response));
    }

    @GetMapping("/{surveyId}")
    public ResponseEntity<CustomApiResponse<SurveyInquiryResponse>> getSurvey(
        @PathVariable(value = "surveyId") Long surveyId
    ) {
        Survey survey = surveyService.findById(surveyId);
        SurveyInquiryResponse response = SurveyInquiryResponse.from(survey);
        return ResponseEntity.ok()
            .body(CustomApiResponse.onSuccess(response));
    }

    @PostMapping("/{surveyId}/survey-item")
    public ResponseEntity<CustomApiResponse<SurveyItemInquiryResponse>> createSurveyItem(
        @PathVariable(value = "surveyId") Long surveyId,
        @RequestBody SurveyItemCreateRequest request
    ) {
        SurveyItem surveyItem = request.toDomain();
        SurveyItemInquiryResponse response = SurveyItemInquiryResponse.from(surveyItemService.createSurveyItem(surveyId, surveyItem));
        return ResponseEntity.ok()
            .body(CustomApiResponse.onSuccess(response));
    }

    @PatchMapping("/{surveyId}/survey-item/{surveyItemId}")
    public ResponseEntity<CustomApiResponse<SurveyItemInquiryResponse>> updateSurveyItem(
        @PathVariable(value = "surveyId") Long surveyId,
        @PathVariable(value = "surveyItemId") Long surveyItemId,
        @RequestBody SurveyItemUpdateRequest request
    ) {
        SurveyItem surveyItem = surveyItemService.updateSurveyItem(surveyId, surveyItemId, request);
        SurveyItemInquiryResponse response = SurveyItemInquiryResponse.from(surveyItem);
        return ResponseEntity.ok()
            .body(CustomApiResponse.onSuccess(response));
    }

    @PostMapping("/{surveyId}/survey-item/{surveyItemId}/{surveyItemVersion}/survey-submission")
    public ResponseEntity<CustomApiResponse<Void>> createSurveySubmission(
        @PathVariable(value = "surveyId") Long surveyId,
        @PathVariable(value = "surveyItemId") Long surveyItemId,
        @PathVariable(value = "surveyItemVersion") int surveyItemVersion,
        @RequestBody SurveySubmissionCreateRequest request
    ) {
       surveySubmissionService.createSurveySubmission(surveyId, surveyItemId, surveyItemVersion, request);
       return ResponseEntity.ok()
           .body(CustomApiResponse.onSuccess());
    }

    @GetMapping("/{surveyId}/response")
    public ResponseEntity<CustomApiResponse<List<SurveyItemAndSubmissionInquiryResponse>>> getAllSurveyItemAndSubmissionOfSurvey(
        @PathVariable(value = "surveyId") Long surveyId
    ) {
        List<SurveyItemAndSubmissionInquiryResponse> response = surveyService.findAllSurveyItemAndSubmission(surveyId);
        return ResponseEntity.ok()
            .body(CustomApiResponse.onSuccess(response));
    }

}
