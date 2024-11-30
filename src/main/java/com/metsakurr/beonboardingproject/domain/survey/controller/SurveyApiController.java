package com.metsakurr.beonboardingproject.domain.survey.controller;

import com.metsakurr.beonboardingproject.common.dto.ApiResponse;
import com.metsakurr.beonboardingproject.domain.survey.dto.SurveyCreationResponse;
import com.metsakurr.beonboardingproject.domain.survey.dto.SurveyRequest;
import com.metsakurr.beonboardingproject.domain.survey.service.SurveyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/survey")
public class SurveyApiController {
    private final SurveyService surveyService;

    @PostMapping
    public ResponseEntity<ApiResponse<SurveyCreationResponse>> create(
            @Valid @RequestBody SurveyRequest surveyRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(surveyService.create(surveyRequest));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<SurveyCreationResponse>> update(
            @Valid @RequestBody SurveyRequest surveyRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(surveyService.update(surveyRequest));
    }
}
