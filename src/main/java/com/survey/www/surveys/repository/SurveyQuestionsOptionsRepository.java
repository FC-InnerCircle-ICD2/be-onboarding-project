package com.survey.www.surveys.repository;

import com.survey.www.surveys.domain.SurveyQuestionOptions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyQuestionsOptionsRepository extends JpaRepository<SurveyQuestionOptions, Long> {
}
