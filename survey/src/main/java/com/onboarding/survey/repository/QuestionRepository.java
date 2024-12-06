package com.onboarding.survey.repository;

import com.onboarding.survey.entity.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

  List<Question> findBySurveyId(Long surveyId);
}
