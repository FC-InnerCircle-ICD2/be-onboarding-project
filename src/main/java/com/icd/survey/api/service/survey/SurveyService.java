package com.icd.survey.api.service.survey;

import com.icd.survey.api.dto.survey.request.SurveyItemRequest;
import com.icd.survey.api.dto.survey.request.SurveyRequest;
import com.icd.survey.api.entity.dto.SurveyDto;
import com.icd.survey.api.entity.dto.SurveyItemDto;
import com.icd.survey.api.entity.survey.Survey;
import com.icd.survey.api.repository.survey.ResponseOptionRepository;
import com.icd.survey.api.repository.survey.SurveyItemRepository;
import com.icd.survey.api.repository.survey.SurveyRepository;
import com.icd.survey.common.CommonUtils;
import com.icd.survey.exception.ApiException;
import com.icd.survey.exception.response.emums.ExceptionResponseType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final SurveyItemRepository surveyItemRepository;
    private final ResponseOptionRepository responseOptionRepository;

    public void createSurvey(SurveyRequest request) {

    }

}
