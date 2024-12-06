package org.innercircle.surveyapiapplication.domain.survey.application;

import lombok.RequiredArgsConstructor;
import org.innercircle.surveyapiapplication.domain.survey.domain.Survey;
import org.innercircle.surveyapiapplication.domain.survey.infrastructure.SurveyRepository;
import org.innercircle.surveyapiapplication.domain.survey.presentation.dto.SurveyItemAndSubmissionInquiryResponse;
import org.innercircle.surveyapiapplication.domain.survey.presentation.dto.SurveyUpdateRequest;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.SurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.infrastructure.SurveyItemRepository;
import org.innercircle.surveyapiapplication.domain.surveySubmission.domain.SurveySubmission;
import org.innercircle.surveyapiapplication.domain.surveySubmission.infrastructure.SurveySubmissionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final SurveyItemRepository surveyItemRepository;
    private final SurveySubmissionRepository surveySubmissionRepository;

    public Survey createSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    public Survey findById(Long surveyId) {
        return surveyRepository.findById(surveyId);
    }

    public List<SurveyItemAndSubmissionInquiryResponse> findAllSurveyItemAndSubmission(Long surveyId) {
        List<SurveyItemAndSubmissionInquiryResponse> result = new ArrayList<>();
        List<SurveyItem> surveyItems = surveyItemRepository.findBySurveyId(surveyId);
        surveyItems.forEach(s -> {
            List<SurveySubmission<?>> surveySubmissions =
                surveySubmissionRepository.findBySurveyItemIdAndVersion(s.getId(), s.getVersion());
            SurveyItemAndSubmissionInquiryResponse response = SurveyItemAndSubmissionInquiryResponse.from(s, surveySubmissions);
            result.add(response);
        });
        return result;
    }

    // Todo : 동시성에 따른 낙관적락
    public Survey updateSurvey(Long surveyId, SurveyUpdateRequest request) {
        Survey survey = surveyRepository.findById(surveyId);
        survey.update(request.name(), request.description());
        return surveyRepository.save(survey);
    }

}
