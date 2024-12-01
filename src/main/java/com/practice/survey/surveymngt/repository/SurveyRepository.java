package com.practice.survey.surveymngt.repository;

import com.practice.survey.surveymngt.model.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    boolean existsByName(String surveyName);

    Survey findByName(String surveyName);
}
