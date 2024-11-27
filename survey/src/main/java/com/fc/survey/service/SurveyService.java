package com.fc.survey.service;

import com.fc.survey.dto.SurveyDTO;
import com.fc.survey.entity.Survey;
import com.fc.survey.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SurveyService {

  private final SurveyRepository surveyRepository;

  @Transactional
  public void saveSurvey(SurveyDTO requestDTO) {
    Survey survey = requestDTO.toEntity();
    surveyRepository.save(survey);
  }
}
