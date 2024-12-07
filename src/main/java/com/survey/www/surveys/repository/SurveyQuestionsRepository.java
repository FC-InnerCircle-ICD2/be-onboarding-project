package com.survey.www.surveys.repository;

import com.survey.www.surveys.domain.SurveyQuestions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyQuestionsRepository extends JpaRepository<SurveyQuestions, Long>, SurveyQuestionsDslRepository {
}
