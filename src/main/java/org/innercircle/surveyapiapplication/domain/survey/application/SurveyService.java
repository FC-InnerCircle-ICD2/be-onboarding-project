package org.innercircle.surveyapiapplication.domain.survey.application;

import lombok.RequiredArgsConstructor;
import org.innercircle.surveyapiapplication.domain.survey.domain.Survey;
import org.innercircle.surveyapiapplication.domain.survey.infrastructure.SurveyRepository;
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

}
