package com.practice.survey.response.repository;

import com.practice.survey.response.model.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseRepository extends JpaRepository<Response, Long> {
//    boolean existsBySurveyName(String surveyName);
//    Response findBySurveyName(String surveyName);
}
