package net.gentledot.survey.application.service.out;

import net.gentledot.survey.domain.surveyanswer.SurveyAnswer;
import net.gentledot.survey.infra.repository.jpa.SurveyAnswerJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SurveyAnswerRepositoryImpl implements SurveyAnswerRepository {
    private final SurveyAnswerJpaRepository surveyAnswerJpaRepository;

    public SurveyAnswerRepositoryImpl(SurveyAnswerJpaRepository surveyAnswerJpaRepository) {
        this.surveyAnswerJpaRepository = surveyAnswerJpaRepository;
    }

    @Override
    public SurveyAnswer save(SurveyAnswer surveyAnswer) {
        return surveyAnswerJpaRepository.save(surveyAnswer);
    }

    @Override
    public List<SurveyAnswer> findAllBySurveyId(String surveyId) {
        return surveyAnswerJpaRepository.findAllBySurveyId(surveyId);
    }
}
