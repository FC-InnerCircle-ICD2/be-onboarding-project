package net.gentledot.survey.service;


import net.gentledot.survey.dto.request.SurveyCreateRequest;
import net.gentledot.survey.dto.request.SurveyQuestionRequest;
import net.gentledot.survey.dto.request.SurveyRequest;
import net.gentledot.survey.dto.request.SurveyUpdateRequest;
import net.gentledot.survey.dto.response.SurveyCreateResponse;
import net.gentledot.survey.dto.response.SurveyUpdateResponse;
import net.gentledot.survey.exception.ServiceError;
import net.gentledot.survey.exception.SurveyCreationException;
import net.gentledot.survey.exception.SurveyNotFoundException;
import net.gentledot.survey.model.entity.surveybase.Survey;
import net.gentledot.survey.model.entity.surveybase.SurveyQuestion;
import net.gentledot.survey.model.enums.SurveyItemType;
import net.gentledot.survey.repository.SurveyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SurveyService {
    public static final int MAXIMUM_QUESTION_COUNT = 10;
    private final SurveyRepository surveyRepository;

    public SurveyService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @Transactional
    public SurveyCreateResponse createSurvey(SurveyCreateRequest surveyRequest) {
        validateRequest(surveyRequest);

        List<SurveyQuestion> questions = convertToSurveyQuestions(surveyRequest.getQuestions());

        Survey survey = Survey.of(
                surveyRequest.getName(),
                surveyRequest.getDescription(),
                questions
        );

        Survey saved = surveyRepository.save(survey);

        return new SurveyCreateResponse(saved.getId(), saved.getCreatedAt());
    }

    @Transactional
    public SurveyUpdateResponse updateSurvey(SurveyUpdateRequest surveyRequest) {
        validateRequest(surveyRequest);

        String surveyId = surveyRequest.getId();
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new SurveyNotFoundException(ServiceError.INQUIRY_SURVEY_NOT_FOUND));


        survey.updateSurvey(surveyRequest.getName(), surveyRequest.getDescription(), surveyRequest.getQuestions());

        Survey saved = surveyRepository.save(survey);

        return new SurveyUpdateResponse(saved.getId(), saved.getUpdatedAt());
    }


    private List<SurveyQuestion> convertToSurveyQuestions(List<SurveyQuestionRequest> questionRequests) {
        return questionRequests.stream()
                .map(SurveyQuestion::from)
                .collect(Collectors.toList());
    }

    private void validateRequest(SurveyRequest surveyRequest) {
        List<SurveyQuestionRequest> questions = surveyRequest.getQuestions();

        if (questions == null) {
            throw new SurveyCreationException(ServiceError.CREATION_INVALID_REQUEST);
        } else if (questions.isEmpty() || questions.size() > MAXIMUM_QUESTION_COUNT) {
            throw new SurveyCreationException(ServiceError.CREATION_INSUFFICIENT_QUESTIONS);
        }

        Map<Long, Long> questionCountMap = new HashMap<>();
        for (SurveyQuestionRequest question : questions) {
            Long questionId = question.getQuestionId();
            if (questionId != null) {
                questionCountMap.put(
                        questionId,
                        questionCountMap.getOrDefault(questionId, 0L) + 1
                );
            }

            if ((question.getType() == SurveyItemType.SINGLE_SELECT || question.getType() == SurveyItemType.MULTI_SELECT)
                && (question.getOptions() == null || question.getOptions().isEmpty())) {
                throw new SurveyCreationException(ServiceError.CREATION_INSUFFICIENT_OPTIONS);
            }
        }

        questionCountMap.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .findFirst()
                .ifPresent(entry -> {
                    throw new SurveyCreationException(ServiceError.CREATION_DUPLICATE_QUESTIONS);
                });
    }


}
