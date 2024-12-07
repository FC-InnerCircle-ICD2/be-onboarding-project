package com.practice.survey.surveymngt.repository;

import com.practice.survey.surveymngt.model.dto.SurveyResponseDto;
import com.practice.survey.surveymngt.model.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    Survey findBySurveyId(Long surveyId);

    Survey findByName(String surveyName);

    boolean existsByName(String surveyName);

}
