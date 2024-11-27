package com.icd.survey.api.repository.survey;

import com.icd.survey.api.entity.survey.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
    Optional<Survey> findBySurveyNameAndIpAddress(String surveyName, String ipAddress);

}
