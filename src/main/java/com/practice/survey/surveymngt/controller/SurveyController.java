package com.practice.survey.surveymngt.controller;

import com.practice.survey.common.response.ApiResponse;
import com.practice.survey.common.response.StatusEnum;
import com.practice.survey.surveymngt.model.dto.SurveyRequestDto;
import com.practice.survey.surveymngt.service.SurveyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/api/v1/survey")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    @PostMapping("/createSurvey")
    public ApiResponse<StatusEnum> createSurvey(@Valid @RequestBody SurveyRequestDto surveyRequestDto) {
        return surveyService.createSurvey(surveyRequestDto);
    }

    @PutMapping("/updateSurvey")
    public ApiResponse<StatusEnum> updateSurvey(@Valid @RequestBody SurveyRequestDto surveyRequestDto) {
        return surveyService.updateSurvey(surveyRequestDto);
    }
}
