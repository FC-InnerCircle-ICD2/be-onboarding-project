package com.onboarding.beonboardingproject.repository;

import com.onboarding.beonboardingproject.domain.response.Response;
import com.onboarding.beonboardingproject.domain.survey.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {
    List<Response> findBySurveyId(Long surveyId);

    List<Response> findBySurvey(Survey survey);
}
