package com.metsakurr.beonboardingproject.domain.survey.controller;

import com.metsakurr.beonboardingproject.domain.survey.dto.SurveyRequest;
import com.metsakurr.beonboardingproject.domain.survey.entity.Survey;
import com.metsakurr.beonboardingproject.domain.survey.service.SurveyService;
import lombok.RequiredArgsConstructor;
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
    public Survey regist(@RequestBody SurveyRequest surveyRequest) {
        return surveyService.regist(surveyRequest);
    }
}
