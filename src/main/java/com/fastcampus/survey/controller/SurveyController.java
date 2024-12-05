package com.fastcampus.survey.controller;

import com.fastcampus.survey.dto.SurveyDto;
import com.fastcampus.survey.entity.Survey;
import com.fastcampus.survey.repository.SurveyRepository;
import com.fastcampus.survey.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/surveys")
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    @PostMapping(path = "/create")
    public String createSurvey(@RequestBody SurveyDto survey) throws Exception {
        return surveyService.createSurvey(survey);
    }

    @PostMapping(path = "/update")
    public String updateSurvey(@RequestBody SurveyDto survey) throws Exception {
        return surveyService.updateSurvey(survey);
    }

}
