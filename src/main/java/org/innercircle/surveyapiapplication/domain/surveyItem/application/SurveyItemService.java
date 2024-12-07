package org.innercircle.surveyapiapplication.domain.surveyItem.application;

import lombok.RequiredArgsConstructor;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.SurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.infrastructure.SurveyItemRepository;
import org.innercircle.surveyapiapplication.domain.survey.domain.Survey;
import org.innercircle.surveyapiapplication.domain.survey.infrastructure.SurveyRepository;
import org.innercircle.surveyapiapplication.domain.surveyItem.presentation.dto.SurveyItemUpdateRequest;
import org.innercircle.surveyapiapplication.global.aop.RetryOnOptimisticLock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SurveyItemService {

    private final SurveyRepository surveyRepository;
    private final SurveyItemRepository surveyItemRepository;

    @Transactional
    @RetryOnOptimisticLock(maxRetries = 3)
    public SurveyItem createSurveyItem(Long surveyId, SurveyItem surveyItem) {
        Survey survey = surveyRepository.findById(surveyId);
        survey.addSurveyItem(surveyItem);
        surveyItem = surveyItemRepository.save(surveyItem);
        return surveyItem;
    }

    public SurveyItem findByIdAndVersion(Long surveyId, Long surveyItemId, int surveyItemVersion) {
        return surveyItemRepository.findByIdAndVersion(surveyId, surveyItemId, surveyItemVersion);
    }

    public SurveyItem updateSurveyItem(Long surveyId, Long surveyItemId, SurveyItemUpdateRequest request) {
        SurveyItem surveyItem = surveyItemRepository.findLatestSurveyItemBySurveyIdAndSurveyItemId(surveyId, surveyItemId);
        SurveyItem updatedItem = surveyItem.update(request.name(), request.description(), request.type(), request.required(), request.options());
        updatedItem = surveyItemRepository.save(updatedItem);
        return updatedItem;
    }

}
