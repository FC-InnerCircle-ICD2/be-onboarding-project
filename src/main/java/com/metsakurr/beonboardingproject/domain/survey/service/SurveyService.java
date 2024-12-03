package com.metsakurr.beonboardingproject.domain.survey.service;

import com.metsakurr.beonboardingproject.common.dto.ApiResponse;
import com.metsakurr.beonboardingproject.common.enums.ResponseCode;
import com.metsakurr.beonboardingproject.common.exception.ServiceException;
import com.metsakurr.beonboardingproject.domain.survey.dto.SurveyCreationResponse;
import com.metsakurr.beonboardingproject.domain.survey.dto.SurveyRequest;
import com.metsakurr.beonboardingproject.domain.survey.entity.Survey;
import com.metsakurr.beonboardingproject.domain.survey.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;

    public ApiResponse<SurveyCreationResponse> create(SurveyRequest request) {
        Survey survey = request.toEntity();
        surveyRepository.save(survey);

        SurveyCreationResponse response = new SurveyCreationResponse(survey);
        return new ApiResponse<>(ResponseCode.SUCCESS, response);
    }

    public ApiResponse<SurveyCreationResponse> update(SurveyRequest request) {
        final long surveyIdx = request.getIdx();

        Survey survey = surveyRepository.findById(surveyIdx)
                .orElseThrow(() -> new ServiceException(ResponseCode.NOT_FOUND_SURVEY));

        survey.update(request);
        Survey newSurvey = surveyRepository.save(survey);
        SurveyCreationResponse response = new SurveyCreationResponse(newSurvey);
        return new ApiResponse<>(ResponseCode.SUCCESS, response);
    }

}
