package com.practice.survey.surveymngt.service;

import com.practice.survey.common.response.ApiResponse;
import com.practice.survey.common.response.StatusEnum;
import com.practice.survey.surveymngt.model.dto.SurveySaveRequestDto;

public interface SurveyService {

    public ApiResponse<StatusEnum> createSurvey(SurveySaveRequestDto surveySaveRequestDto);

}
