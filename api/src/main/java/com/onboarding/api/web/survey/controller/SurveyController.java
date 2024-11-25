package com.onboarding.api.web.survey.controller;

import com.onboarding.api.web.survey.dto.CreateSurveyReqDto;
import com.onboarding.survey.survey.facade.SurveyFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/survey")
@RequiredArgsConstructor
public class SurveyController {
  private final SurveyFacade surveyFacade;

  @PostMapping
  public ResponseEntity<?> createSurvey(CreateSurveyReqDto req) {

  }

}
