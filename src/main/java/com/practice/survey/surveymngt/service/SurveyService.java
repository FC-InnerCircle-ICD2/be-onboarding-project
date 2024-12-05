package com.practice.survey.surveymngt.service;

import com.practice.survey.common.response.ApiResponse;
import com.practice.survey.common.response.StatusEnum;
import com.practice.survey.surveyVersion.model.dto.SurveyVersionSaveRequestDto;
import com.practice.survey.surveyVersion.model.entity.SurveyVersion;
import com.practice.survey.surveymngt.model.dto.SurveyRequestDto;
import com.practice.survey.surveymngt.model.dto.SurveyResponseDto;
import com.practice.survey.surveymngt.model.entity.Survey;

import java.util.List;

public interface SurveyService {

    public ApiResponse<StatusEnum> createSurvey(SurveyRequestDto surveyRequestDto);

    public ApiResponse<StatusEnum> updateSurvey(SurveyRequestDto surveyRequestDto);

    public ApiResponse<List<SurveyResponseDto>> getSurveyResponse(Long surveyId);
}
