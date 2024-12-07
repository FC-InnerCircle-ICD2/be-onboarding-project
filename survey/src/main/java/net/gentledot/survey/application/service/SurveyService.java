package net.gentledot.survey.application.service;


import net.gentledot.survey.application.service.in.model.request.SurveyCreateRequest;
import net.gentledot.survey.application.service.in.model.request.SurveyQuestionRequest;
import net.gentledot.survey.application.service.in.model.request.SurveyUpdateRequest;
import net.gentledot.survey.application.service.in.model.response.SurveyCreateResponse;
import net.gentledot.survey.application.service.in.model.response.SurveyUpdateResponse;
import net.gentledot.survey.application.service.out.SurveyRepository;
import net.gentledot.survey.application.service.util.SurveyValidator;
import net.gentledot.survey.domain.enums.UpdateType;
import net.gentledot.survey.domain.exception.ServiceError;
import net.gentledot.survey.domain.exception.SurveyNotFoundException;
import net.gentledot.survey.domain.surveybase.Survey;
import net.gentledot.survey.domain.surveybase.SurveyQuestion;
import net.gentledot.survey.domain.surveybase.dto.SurveyQuestionDto;
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

        List<SurveyQuestionRequest> generateTargetQuestion = surveyRequest.getQuestions();
        List<SurveyQuestion> questions = generateTargetQuestion.stream()
                .map(SurveyQuestionDto::from)
                .map(SurveyQuestion::from)
                .collect(Collectors.toList());


        Survey survey = Survey.of(surveyRequest.getName(), surveyRequest.getDescription(), questions);
        Survey saved = surveyRepository.save(survey);

        return SurveyCreateResponse.of(saved.getId(), saved.getCreatedAt(), saved.getQuestions());
    }

    @Transactional
    public SurveyUpdateResponse updateSurvey(SurveyUpdateRequest surveyRequest) {
        SurveyValidator.validateRequest(surveyRequest);

        String surveyId = surveyRequest.getId();
        Survey survey = surveyRepository.findById(surveyId);

        survey.updateSurveyNameAndDesc(surveyRequest.getName(), surveyRequest.getDescription());

        for (SurveyQuestionRequest questionRequest : surveyRequest.getQuestions()) {
            SurveyQuestionDto questionForUpdate = SurveyQuestionDto.from(questionRequest);
            UpdateType updateType = questionRequest.getUpdateType();
            if (updateType == null) {
                updateType = UpdateType.ADD;
            }
            switch (updateType) {
                case MODIFY:
                    SurveyQuestion existingQuestion = survey.getQuestions().stream()
                            .filter(question -> question.getId().equals(questionRequest.getQuestionId()))
                            .findFirst()
                            .orElseThrow(() -> new SurveyNotFoundException(ServiceError.INQUIRY_QUESTION_NOT_FOUND));

                    existingQuestion.updateFromRequest(questionForUpdate);
                    break;
                case DELETE:
                    SurveyQuestion questionToRemove = survey.getQuestions().stream()
                            .filter(q -> q.getId().equals(questionRequest.getQuestionId()))
                            .findFirst()
                            .orElseThrow(() -> new SurveyNotFoundException(ServiceError.INQUIRY_QUESTION_NOT_FOUND));
                    survey.removeQuestion(questionToRemove);
                    break;
                default:
                    SurveyQuestion newQuestion = SurveyQuestion.from(questionForUpdate);
                    survey.addQuestion(newQuestion);
                    break;
            }
        }

        Survey saved = surveyRepository.save(survey);

        return SurveyUpdateResponse.of(saved.getId(), saved.getUpdatedAt(), saved.getQuestions());

    }
}
