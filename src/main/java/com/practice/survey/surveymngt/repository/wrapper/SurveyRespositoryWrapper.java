package com.practice.survey.surveymngt.repository.wrapper;

import com.practice.survey.surveymngt.model.dto.SurveyResponseDto;

import java.util.List;

public interface SurveyRespositoryWrapper {
     public List<SurveyResponseDto> getSurveyResponse(Long surveyId);
}
