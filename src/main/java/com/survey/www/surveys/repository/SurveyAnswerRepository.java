package com.survey.www.surveys.repository;

import com.survey.www.surveys.domain.SurveyAnswers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyAnswerRepository extends JpaRepository<SurveyAnswers, Long> {
}
