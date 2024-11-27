package com.fc.survey.controller;

import com.fc.survey.dto.SurveyDTO;
import com.fc.survey.service.SurveyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/survey")
@RequiredArgsConstructor
@Slf4j
public class SurveyController {

  private final SurveyService surveyService;

  @PostMapping
  public String saveSurvey(@RequestBody SurveyDTO request){
    log.info("call saveSurvey: {}", request);
    surveyService.saveSurvey(request);
    return "등록 완료";
  }

}
