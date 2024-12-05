package org.innercircle.surveyapiapplication.domain.survey.application;

import lombok.RequiredArgsConstructor;
import org.innercircle.surveyapiapplication.domain.survey.domain.Survey;
import org.innercircle.surveyapiapplication.domain.survey.infrastructure.SurveyRepository;
import org.innercircle.surveyapiapplication.domain.survey.presentation.dto.SurveyUpdateRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;

    public Survey createSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    public Survey findById(Long surveyId) {
        return surveyRepository.findById(surveyId);
    }

    // Todo : 동시성에 따른 낙관적락
    public Survey updateSurvey(Long surveyId, SurveyUpdateRequest request) {
        Survey survey = surveyRepository.findById(surveyId);
        survey.update(request.name(), request.description());
        return survey;
    }

}
