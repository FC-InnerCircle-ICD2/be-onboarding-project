package org.innercircle.surveyapiapplication.domain.question.infrastructure;

import org.innercircle.surveyapiapplication.domain.question.domain.Question;

import java.util.List;

public interface QuestionRepository {

    Question save(Question question);

    List<Question> findBySurveyId(Long surveyId);

    Question findById(Long questionId);

}
