package org.innercircle.surveyapiapplication.domain.surveySubmission.application;

import lombok.RequiredArgsConstructor;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.SurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.infrastructure.SurveyItemRepository;
import org.innercircle.surveyapiapplication.domain.surveySubmission.domain.SurveySubmission;
import org.innercircle.surveyapiapplication.domain.surveySubmission.infrastructure.SurveySubmissionRepository;
import org.innercircle.surveyapiapplication.domain.surveySubmission.presentation.dto.SurveySubmissionCreateRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SurveySubmissionService {

    private final SurveySubmissionRepository surveySubmissionRepository;
    private final SurveyItemRepository surveyItemRepository;

    public SurveySubmission<?> createSurveySubmission(Long surveyId, Long surveyItemId, int surveyItemVersion, SurveySubmissionCreateRequest request) {
        SurveyItem surveyItem = surveyItemRepository.findByIdAndVersion(surveyId, surveyItemId, surveyItemVersion);
        SurveySubmission<?> surveySubmission = request.toDomain(surveyItem.getType());
        surveySubmission = surveySubmission.setSurveyItemIdAndVersion(surveyItem);
        surveySubmission = surveySubmissionRepository.save(surveySubmission);
        return surveySubmission;
    }

}
