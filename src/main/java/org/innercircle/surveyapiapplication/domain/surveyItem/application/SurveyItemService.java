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
    public SurveyItem createQuestion(Long surveyId, SurveyItem surveyItem) {
        Survey survey = surveyRepository.findById(surveyId);
        survey.addQuestion(surveyItem);
        surveyItem = surveyItemRepository.save(surveyItem);
        surveyRepository.save(survey); // 낙관적락 동작을 위한 코드...
        return surveyItem;
    }

    public SurveyItem findByIdAndVersion(Long surveyId, Long questionId, int questionVersion) {
        return surveyItemRepository.findByIdAndVersion(surveyId, questionId, questionVersion);
    }

    public SurveyItem updateQuestion(Long surveyId, Long questionId, SurveyItemUpdateRequest request) {
        SurveyItem surveyItem = surveyItemRepository.findLatestQuestionBySurveyIdAndSurveyItemId(surveyId, questionId);
        SurveyItem updatedItem = surveyItem.update(request.name(), request.description(), request.type(), request.required(), request.options());
        updatedItem = surveyItemRepository.save(updatedItem);
        return updatedItem;
    }

}
