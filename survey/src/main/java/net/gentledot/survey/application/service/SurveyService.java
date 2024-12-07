package net.gentledot.survey.application.service;


import net.gentledot.survey.application.service.in.model.request.SurveyCreateRequest;
import net.gentledot.survey.application.service.in.model.request.SurveyUpdateRequest;
import net.gentledot.survey.application.service.in.model.response.SurveyCreateResponse;
import net.gentledot.survey.application.service.in.model.response.SurveyUpdateResponse;
import net.gentledot.survey.application.service.out.SurveyRepository;
import net.gentledot.survey.application.service.util.SurveyValidator;
import net.gentledot.survey.domain.surveybase.Survey;
import net.gentledot.survey.domain.surveybase.SurveyQuestion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SurveyService {
    private final SurveyRepository surveyRepository;

    public SurveyService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @Transactional
    public SurveyCreateResponse createSurvey(SurveyCreateRequest surveyRequest) {
        SurveyValidator.validateRequest(surveyRequest);
        List<SurveyQuestion> questions = surveyRequest.getQuestions().stream().map(SurveyQuestion::from).collect(Collectors.toList());

        Survey survey = Survey.of(surveyRequest.getName(), surveyRequest.getDescription(), questions);
        Survey saved = surveyRepository.save(survey);

        return SurveyCreateResponse.of(saved.getId(), saved.getCreatedAt(), saved.getQuestions());
    }

    @Transactional
    public SurveyUpdateResponse updateSurvey(SurveyUpdateRequest surveyRequest) {
        SurveyValidator.validateRequest(surveyRequest);

        String surveyId = surveyRequest.getId();
        Survey survey = surveyRepository.findById(surveyId);

        survey.updateSurvey(surveyRequest.getName(), surveyRequest.getDescription(), surveyRequest.getQuestions());

        Survey saved = surveyRepository.save(survey);

        return SurveyUpdateResponse.of(saved.getId(), saved.getUpdatedAt(), saved.getQuestions());

    }
}
