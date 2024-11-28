package com.onboarding.survey.service.create;

import com.onboarding.survey.entity.Survey;
import com.onboarding.survey.repository.SurveyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CreateSurveyService {

  private final SurveyRepository surveyRepository;

  public CreateSurveyService(SurveyRepository surveyRepository) {
    this.surveyRepository = surveyRepository;
  }

  public void createSurvey(Survey survey) {
    surveyRepository.save(survey);
    log.info("Creating survey");
  }
}
