package net.gentledot.survey.service.out;

import net.gentledot.survey.domain.surveyanswer.SurveyAnswer;
import net.gentledot.survey.domain.surveybase.Survey;
import net.gentledot.survey.repository.jpa.SurveyAnswerJpaRepository;
import net.gentledot.survey.service.in.model.request.SubmitSurveyAnswer;
import org.springframework.stereotype.Repository;

import java.util.List;

import static net.gentledot.survey.service.util.SurveyVaildator.validateSurveyAnswers;

@Repository
public class SurveyAnswerOut implements SurveyAnswerRepository {
    private final SurveyOut surveyOut;
    private final SurveyAnswerJpaRepository surveyAnswerJpaRepository;

    public SurveyAnswerOut(SurveyOut surveyOut, SurveyAnswerJpaRepository surveyAnswerJpaRepository) {
        this.surveyOut = surveyOut;
        this.surveyAnswerJpaRepository = surveyAnswerJpaRepository;
    }

    @Override
    public SurveyAnswer save(String surveyId, List<SubmitSurveyAnswer> answers) {
        Survey survey = surveyOut.findById(surveyId);

        // 설문조사 항목과 응답 값 검증
        validateSurveyAnswers(survey, answers);

        SurveyAnswer surveyAnswer = SurveyAnswer.of(survey, answers);
        return surveyAnswerJpaRepository.save(surveyAnswer);
    }

    @Override
    public List<SurveyAnswer> findAllBySurveyId(String surveyId) {
        return surveyAnswerJpaRepository.findAllBySurveyId(surveyId);
    }
}
