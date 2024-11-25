package com.onboarding.survey.survey.repository;

import com.onboarding.survey.survey.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

}
