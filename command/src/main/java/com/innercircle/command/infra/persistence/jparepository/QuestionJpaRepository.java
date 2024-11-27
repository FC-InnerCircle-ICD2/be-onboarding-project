package com.innercircle.command.infra.persistence.jparepository;

import com.innercircle.command.domain.survey.question.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionJpaRepository extends JpaRepository<Question, String> {

	List<Question> findBySurveyId(String surveyId);
}
