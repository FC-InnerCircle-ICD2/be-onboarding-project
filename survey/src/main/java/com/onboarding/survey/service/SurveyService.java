package com.onboarding.survey.service;

import com.onboarding.core.global.exception.CustomException;
import com.onboarding.core.global.exception.enums.ErrorCode;
import com.onboarding.survey.entity.Survey;
import com.onboarding.survey.repository.QuestionRepository;
import com.onboarding.survey.repository.SurveyRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SurveyService {

  private final SurveyRepository surveyRepository;
  private final QuestionRepository questionRepository;

  public void createSurvey(Survey survey) {
    surveyRepository.save(survey);
    log.info("Creating survey");
  }

  public Survey getSurveyById(Long surveyId) {
    return surveyRepository.findById(surveyId).orElseThrow(() -> new CustomException(
        "Required question is missing: ", ErrorCode.ENTITY_NOT_FOUND));
  }

  public Optional<Survey> findSurveyById(Long surveyId) {
    return surveyRepository.findById(surveyId);
  }
}
