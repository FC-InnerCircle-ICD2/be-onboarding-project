package org.innercircle.surveyapiapplication.domain.survey.infrastructure;

import lombok.RequiredArgsConstructor;
import org.innercircle.surveyapiapplication.domain.question.domain.Question;
import org.innercircle.surveyapiapplication.domain.question.infrastructure.QuestionRepository;
import org.innercircle.surveyapiapplication.domain.survey.domain.Survey;
import org.innercircle.surveyapiapplication.domain.survey.entity.SurveyEntity;
import org.innercircle.surveyapiapplication.global.exception.CustomException;
import org.innercircle.surveyapiapplication.global.exception.CustomExceptionStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SurveyRepositoryImpl implements SurveyRepository {

    private final SurveyJpaRepository surveyJpaRepository;
    private final QuestionRepository questionRepository;

    @Override
    public Survey save(Survey survey) {
        return surveyJpaRepository.save(SurveyEntity.from(survey)).toDomain(survey.getQuestions());
    }

    @Override
    public Survey findById(Long surveyId) {
        List<Question> questions = questionRepository.findBySurveyId(surveyId);
        return surveyJpaRepository.findById(surveyId)
            .orElseThrow(() -> new CustomException(CustomExceptionStatus.NOT_FOUND_SURVEY))
            .toDomain(questions);
    }

}
