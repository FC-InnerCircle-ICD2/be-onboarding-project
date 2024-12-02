package org.innercircle.surveyapiapplication.domain.survey.infrastructure;

import lombok.RequiredArgsConstructor;
import org.innercircle.surveyapiapplication.domain.question.domain.Question;
import org.innercircle.surveyapiapplication.domain.question.infrastructure.QuestionRepository;
import org.innercircle.surveyapiapplication.domain.survey.domain.Survey;
import org.innercircle.surveyapiapplication.domain.survey.entity.SurveyEntity;
import org.innercircle.surveyapiapplication.global.exception.CustomException;
import org.innercircle.surveyapiapplication.global.exception.CustomResponseStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SurveyRepositoryImpl implements SurveyRepository {

    private final SurveyJpaRepository surveyJpaRepository;
    private final QuestionRepository questionRepository;

    @Override
    @Transactional
    public Survey save(Survey survey) {
        SurveyEntity savedSurveyEntity = surveyJpaRepository.save(SurveyEntity.from(survey));
        List<Question> questions = new ArrayList<>();
        for (Question question: survey.getQuestions()) {
            question.setSurveyId(savedSurveyEntity.getId());
            Question savedQuestion = questionRepository.save(question);
            questions.add(savedQuestion);
        }
        return savedSurveyEntity.toDomain(questions);
    }

    @Override
    public Survey findById(Long surveyId) {
        List<Question> questions = questionRepository.findBySurveyId(surveyId);
        return surveyJpaRepository.findById(surveyId)
            .orElseThrow(() -> new CustomException(CustomResponseStatus.NOT_FOUND_SURVEY))
            .toDomain(questions);
    }

}
