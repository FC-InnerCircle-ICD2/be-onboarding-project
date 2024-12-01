package com.practice.survey.surveymngt.controller;

import com.practice.survey.common.response.ApiResponse;
import com.practice.survey.common.response.StatusEnum;
import com.practice.survey.surveymngt.model.dto.SurveySaveRequestDto;
import com.practice.survey.surveymngt.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/v1/survey")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    @PostMapping("/createSurvey")
    public ApiResponse<StatusEnum> createSurvey(@RequestBody SurveySaveRequestDto surveySaveRequestDto) {
        return surveyService.createSurvey(surveySaveRequestDto);
    }
}
