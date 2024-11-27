package com.icd.survey.api.controller.survey;

import com.icd.survey.api.dto.survey.request.SurveyRequest;
import com.icd.survey.api.dto.survey.request.SurveyUpdateRequest;
import com.icd.survey.api.entity.dto.SurveyDto;
import com.icd.survey.api.service.survey.SurveyService;
import com.icd.survey.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
public class SurveyController {
    private final SurveyService surveyService;

    @PostMapping
    public void createSurvey(@Validated @RequestBody SurveyRequest requestDto) {
        surveyService.createSurvey(requestDto);
    }

}
