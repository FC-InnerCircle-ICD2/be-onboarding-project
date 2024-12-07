package com.innercircle.query.infra.persistence.jparepository;

import com.innercircle.query.infra.persistence.model.survey.question.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionJpaRepository extends JpaRepository<Question, String> {

	List<Question> findBySurveyId(String surveyId);
}
