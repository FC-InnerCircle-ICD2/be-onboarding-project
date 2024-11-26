package net.gentledot.survey.service;

import jakarta.transaction.Transactional;
import net.gentledot.survey.dto.request.SurveyGenerateRequest;
import net.gentledot.survey.dto.request.SurveyQuestionRequest;
import net.gentledot.survey.model.entity.Survey;
import net.gentledot.survey.model.entity.SurveyQuestion;
import net.gentledot.survey.repository.SurveyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SurveyService {
    private final SurveyRepository surveyRepository;

    public SurveyService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @Transactional
    public Survey createSurvey(SurveyGenerateRequest surveyRequest) {
        List<SurveyQuestion> questions = convertToSurveyQuestions(surveyRequest.getQuestions());

        Survey survey = Survey.of(
                surveyRequest.getName(),
                surveyRequest.getDescription(),
                questions
        );

        return surveyRepository.save(survey);
    }

    private List<SurveyQuestion> convertToSurveyQuestions(List<SurveyQuestionRequest> questionRequests) {
        return questionRequests.stream()
                .map(SurveyQuestion::from)
                .collect(Collectors.toList());
    }


}
