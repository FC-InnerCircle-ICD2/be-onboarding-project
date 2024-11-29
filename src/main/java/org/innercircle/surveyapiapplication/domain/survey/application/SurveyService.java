package org.innercircle.surveyapiapplication.domain.survey.application;

import lombok.RequiredArgsConstructor;
import org.innercircle.surveyapiapplication.domain.question.infrastructure.QuestionRepository;
import org.innercircle.surveyapiapplication.domain.survey.domain.Survey;
import org.innercircle.surveyapiapplication.domain.survey.infrastructure.SurveyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public void createSurvey(Survey survey) {
        survey.getQuestions().forEach(questionRepository::save);
        surveyRepository.save(survey);
    }

    @Transactional(readOnly = true)
    public Survey findById(Long surveyId) {
        return surveyRepository.findById(surveyId);
    }

}
