package com.onboarding.api.web.survey.controller;

import com.onboarding.api.web.survey.dto.create.SurveyCreateReqDto;
import com.onboarding.api.web.survey.dto.update.SurveyUpdateReqDto;
import com.onboarding.survey.entity.Survey;
import com.onboarding.survey.facade.SurveyFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/surveys")
@RequiredArgsConstructor
public class SurveyController {
  private final SurveyFacade surveyFacade;

  @PostMapping
  public ResponseEntity<?> createSurvey(@RequestBody SurveyCreateReqDto req) {
    surveyFacade.createSurvey(req.surveyObjectOf());
    return ResponseEntity.ok().body("success");
  }

  @PatchMapping("/{surveyId}")
  public ResponseEntity<?> updateSurvey(@PathVariable Long surveyId, @RequestBody
      SurveyUpdateReqDto req) {
    surveyFacade.updateSurvey(surveyId, req.surveyObjectOf());
    return ResponseEntity.ok("Success");
  }

  @PatchMapping("/{surveyId}/questions/{questionId}/swap")
  public ResponseEntity<?> swapQuestionOrder(
      @PathVariable Long surveyId,
      @PathVariable Long questionId,
      @RequestParam("targetQuestionId") Long targetQuestionId
  ) {
    Survey updatedSurvey = surveyFacade.swapQuestionOrder(surveyId, questionId, targetQuestionId);
    return ResponseEntity.ok(updatedSurvey);
  }


}
