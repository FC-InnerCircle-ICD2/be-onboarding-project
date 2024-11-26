package com.onboarding.survey.survey.service.read;

import com.onboarding.core.global.exception.CustomException;
import com.onboarding.core.global.exception.enums.ErrorCode;
import com.onboarding.survey.survey.entity.Survey;
import com.onboarding.survey.survey.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadSurveyService {
  private final SurveyRepository surveyRepository;

  public Survey getSurveyById(Long surveyId) {
    return surveyRepository.findById(surveyId).orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
  }

}
