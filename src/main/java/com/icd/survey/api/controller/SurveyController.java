package com.icd.survey.api.controller;

import com.icd.survey.api.dto.request.SurveyRequest;
import com.icd.survey.api.service.SurveyService;
import com.icd.survey.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
public class SurveyController {
    private final SurveyService surveyService;

    @PostMapping
    public ResponseEntity<ApiResponse> createSurvey(@Validated @RequestBody SurveyRequest requestDto) {
        return ResponseEntity.ok(ApiResponse.successResponse());
    }
}
