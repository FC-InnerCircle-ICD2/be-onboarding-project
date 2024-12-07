package com.practice.survey.surveymngt.service;

import com.practice.survey.common.response.ResponseTemplate;
import com.practice.survey.common.response.StatusEnum;
import com.practice.survey.surveymngt.model.dto.SurveyRequestDto;
import com.practice.survey.surveymngt.model.dto.SurveyResponseDto;

import java.util.List;

public interface SurveyService {

    public ResponseTemplate<StatusEnum> createSurvey(SurveyRequestDto surveyRequestDto);

    public ResponseTemplate<StatusEnum> updateSurvey(SurveyRequestDto surveyRequestDto);

    public ResponseTemplate<List<SurveyResponseDto>> getSurveyResponse(Long surveyId);
}
