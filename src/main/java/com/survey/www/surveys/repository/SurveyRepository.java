package com.survey.www.surveys.repository;

import com.survey.www.surveys.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, Long>, SurveyDslRepository {
}
