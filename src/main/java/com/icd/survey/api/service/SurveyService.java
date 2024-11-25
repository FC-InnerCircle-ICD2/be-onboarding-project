package com.icd.survey.api.service;

import com.icd.survey.api.dto.request.SurveyItemRequest;
import com.icd.survey.api.dto.request.SurveyRequest;
import com.icd.survey.api.entity.Survey;
import com.icd.survey.api.repository.SurveyItemRepository;
import com.icd.survey.api.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final SurveyItemRepository surveyItemRepository;

    private Survey createSurvey(SurveyRequest request) {

        request.validationCheck();
        Survey survey = request.toEntity();

        List<SurveyItemRequest> itemRequestList = request.getSurveyItemList();

        for (SurveyItemRequest itemRequest : itemRequestList) {
            itemRequest.validationCheck();

            if (itemRequest.isChoiceType()) {

            }
        }
        return null;
    }
}
