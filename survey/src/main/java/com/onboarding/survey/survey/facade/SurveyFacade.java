package com.onboarding.survey.survey.facade;

import com.onboarding.survey.survey.dto.SurveyObjectDto;
import com.onboarding.survey.survey.service.create.CreateQuestionService;
import com.onboarding.survey.survey.service.create.CreateSurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class SurveyFacade {
  private final CreateQuestionService createQuestionService;
  private final CreateSurveyService createSurveyService;

  public void createSurvey(SurveyObjectDto surveyObjectDto) {
    createSurveyService.createSurvey();
    createQuestionService.createQuestion();
  }

}
