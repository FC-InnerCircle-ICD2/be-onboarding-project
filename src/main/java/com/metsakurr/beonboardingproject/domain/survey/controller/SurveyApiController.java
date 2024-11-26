package com.metsakurr.beonboardingproject.domain.survey.controller;

import com.metsakurr.beonboardingproject.common.dto.ApiResponse;
import com.metsakurr.beonboardingproject.common.enums.ResponseCode;
import com.metsakurr.beonboardingproject.domain.survey.dto.RegistSurveyResponse;
import com.metsakurr.beonboardingproject.domain.survey.dto.SurveyRequest;
import com.metsakurr.beonboardingproject.domain.survey.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/survey")
public class SurveyApiController {
    private final SurveyService surveyService;

    @PostMapping
    public ResponseEntity<ApiResponse<RegistSurveyResponse>> regist(
            @RequestBody SurveyRequest surveyRequest
    ) {
        RegistSurveyResponse response = surveyService.regist(surveyRequest);
        ApiResponse apiResponse = new ApiResponse(ResponseCode.SUCCESS, response);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}
