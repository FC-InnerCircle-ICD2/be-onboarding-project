package com.innercircle.query.infra.persistence.jparepository;

import com.innercircle.query.infra.persistence.model.survey.response.Answer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerJpaRepository extends JpaRepository<Answer, String> {

	List<Answer> findBySurveyResponseIdIn(List<String> surveyResponseIds);
}
