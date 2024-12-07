package net.gentledot.survey.service.out;

import net.gentledot.survey.domain.surveybase.Survey;
import net.gentledot.survey.domain.surveybase.SurveyQuestion;
import net.gentledot.survey.exception.ServiceError;
import net.gentledot.survey.exception.SurveyNotFoundException;
import net.gentledot.survey.repository.jpa.SurveyJpaRepository;
import net.gentledot.survey.service.in.model.request.SurveyCreateRequest;
import net.gentledot.survey.service.in.model.request.SurveyQuestionRequest;
import net.gentledot.survey.service.in.model.request.SurveyUpdateRequest;
import net.gentledot.survey.service.in.model.response.SurveyCreateResponse;
import net.gentledot.survey.service.in.model.response.SurveyUpdateResponse;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SurveyOut implements SurveyRepository {
    private final SurveyJpaRepository surveyJpaRepository;

    public SurveyOut(SurveyJpaRepository surveyJpaRepository) {
        this.surveyJpaRepository = surveyJpaRepository;
    }

    @Override
    public Survey findById(String surveyId) {
        return surveyJpaRepository.findById(surveyId).orElseThrow(() -> new SurveyNotFoundException(ServiceError.INQUIRY_SURVEY_NOT_FOUND));
    }

    @Override
    public Survey save(Survey survey) {
        return surveyJpaRepository.save(survey);
    }

    public boolean existsById(String surveyId) {
        return surveyJpaRepository.existsById(surveyId);
    }


    public SurveyCreateResponse createSurvey(SurveyCreateRequest surveyRequest) {
        List<SurveyQuestion> questions = convertToSurveyQuestions(surveyRequest.getQuestions());

        Survey survey = Survey.of(surveyRequest.getName(), surveyRequest.getDescription(), questions);
        Survey saved = surveyJpaRepository.save(survey);

        return SurveyCreateResponse.of(saved.getId(), saved.getCreatedAt(), saved.getQuestions());
    }

    private List<SurveyQuestion> convertToSurveyQuestions(List<SurveyQuestionRequest> questionRequests) {
        return questionRequests.stream().map(SurveyQuestion::from).collect(Collectors.toList());
    }

    public SurveyUpdateResponse updateSurvey(SurveyUpdateRequest surveyRequest) {
        String surveyId = surveyRequest.getId();
        Survey survey = surveyJpaRepository.findById(surveyId)
                .orElseThrow(() -> new SurveyNotFoundException(ServiceError.INQUIRY_SURVEY_NOT_FOUND));

        survey.updateSurvey(surveyRequest.getName(), surveyRequest.getDescription(), surveyRequest.getQuestions());

        Survey saved = surveyJpaRepository.save(survey);

        return SurveyUpdateResponse.of(saved.getId(), saved.getUpdatedAt(), saved.getQuestions());

    }
}
