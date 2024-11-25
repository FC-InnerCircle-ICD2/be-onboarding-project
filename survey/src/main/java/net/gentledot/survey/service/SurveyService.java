package net.gentledot.survey.service;

import jakarta.transaction.Transactional;
import net.gentledot.survey.dto.request.SurveyGenerateRequest;
import net.gentledot.survey.dto.request.SurveyQuestionOptionRequest;
import net.gentledot.survey.dto.request.SurveyQuestionRequest;
import net.gentledot.survey.model.entity.Survey;
import net.gentledot.survey.model.entity.SurveyQuestion;
import net.gentledot.survey.model.entity.SurveyQuestionOption;
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
        List<SurveyQuestion> questions = surveyRequest.getQuestions().stream()
                .map(this::convertToSurveyQuestion)
                .collect(Collectors.toList());

        Survey survey = Survey.of(
                surveyRequest.getName(),
                surveyRequest.getDescription(),
                questions
        );

        return surveyRepository.save(survey);
    }

    private SurveyQuestion convertToSurveyQuestion(SurveyQuestionRequest questionRequest) {
        List<SurveyQuestionOption> options = questionRequest.getOptions()
                .stream()
                .map(this::convertToSurveyItemOption)
                .collect(Collectors.toList());

        return SurveyQuestion.of(
                questionRequest.getItemName(),
                questionRequest.getItemDescription(),
                questionRequest.getItemType(),
                questionRequest.getRequired(),
                options
        );
    }

    private SurveyQuestionOption convertToSurveyItemOption(SurveyQuestionOptionRequest optionRequest) {
        return SurveyQuestionOption.of(optionRequest.getOptionText());
    }
}
