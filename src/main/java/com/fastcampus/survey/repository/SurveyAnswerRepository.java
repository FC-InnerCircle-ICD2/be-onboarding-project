package com.fastcampus.survey.repository;

import com.fastcampus.survey.entity.SurveyAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SurveyAnswerRepository extends JpaRepository<SurveyAnswer, Long> {
    Optional<List<SurveyAnswer>> findBySurveyId(Long surveyID);
}
