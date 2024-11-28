package com.icd.survey.api.controller.survey;

import com.icd.survey.api.dto.survey.request.SubmitSurveyRequest;
import com.icd.survey.api.service.survey.SurveyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/survey/submit")
@RequiredArgsConstructor
public class SurveyResponseController {
    private final SurveyService surveyService;

    @PostMapping
    public void submitSurvey(@RequestBody SubmitSurveyRequest request) {

    }
}
