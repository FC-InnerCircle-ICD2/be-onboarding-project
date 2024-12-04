package com.practice.survey.surveymngt.repository;

import com.practice.survey.surveymngt.model.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    Survey findBySurveyId(Long surveyId);

    Survey findByName(String surveyName);

    boolean existsByName(String surveyName);
}
