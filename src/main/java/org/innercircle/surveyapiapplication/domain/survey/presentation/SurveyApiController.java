package org.innercircle.surveyapiapplication.domain.survey.presentation;

import lombok.RequiredArgsConstructor;
import org.innercircle.surveyapiapplication.domain.survey.application.SurveyService;
import org.innercircle.surveyapiapplication.domain.survey.domain.Survey;
import org.innercircle.surveyapiapplication.domain.survey.presentation.dto.SurveyCreateRequest;
import org.innercircle.surveyapiapplication.domain.survey.presentation.dto.SurveyInquiryResponse;
import org.innercircle.surveyapiapplication.global.handler.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
        @RequestBody SurveyCreateRequest surveyCreateRequest
    ) {
       Survey survey = surveyCreateRequest.toDomain();
       SurveyInquiryResponse response = SurveyInquiryResponse.from(surveyService.createSurvey(survey));
       return ResponseEntity.ok()
           .body(ApiResponse.onSuccess(response));
    }

}
