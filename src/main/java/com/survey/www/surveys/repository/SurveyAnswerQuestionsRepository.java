package com.survey.www.surveys.repository;

import com.survey.www.surveys.domain.SurveyAnswerQuestions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyAnswerQuestionsRepository extends JpaRepository<SurveyAnswerQuestions, Long>, SurveyAnswerQuestionsDslRepository {
}
