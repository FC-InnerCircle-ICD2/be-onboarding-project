package com.onboarding.survey.survey.facade;

import com.onboarding.survey.survey.dto.SurveyObjectDto;
import com.onboarding.survey.survey.entity.Survey;
import com.onboarding.survey.survey.service.create.CreateSurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class SurveyFacade {
  private final CreateSurveyService createSurveyService;

  public void createSurvey(SurveyObjectDto surveyObjectDto) {
    Survey survey = surveyObjectDto.of(
        surveyObjectDto.questions().stream().map(questionDto -> questionDto.of(null)).toList()
    );
    survey.getQuestions().forEach(question -> question.setSurvey(survey));
    createSurveyService.createSurvey(survey);
  }

}
