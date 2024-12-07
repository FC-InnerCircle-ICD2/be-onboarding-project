package net.gentledot.survey.application.service.out;

import net.gentledot.survey.domain.exception.ServiceError;
import net.gentledot.survey.domain.exception.SurveyNotFoundException;
import net.gentledot.survey.domain.surveybase.Survey;
import net.gentledot.survey.infra.repository.jpa.SurveyJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class SurveyRepositoryImpl implements SurveyRepository {
    private final SurveyJpaRepository surveyJpaRepository;

    public SurveyRepositoryImpl(SurveyJpaRepository surveyJpaRepository) {
        this.surveyJpaRepository = surveyJpaRepository;
    }

    @Override
    public Survey findById(String surveyId) {
        return surveyJpaRepository.findById(surveyId)
                .orElseThrow(() -> new SurveyNotFoundException(ServiceError.INQUIRY_SURVEY_NOT_FOUND));
    }

    @Override
    public Survey save(Survey survey) {
        return surveyJpaRepository.save(survey);
    }

    @Override
    public boolean existsById(String surveyId) {
        return surveyJpaRepository.existsById(surveyId);
    }
}
