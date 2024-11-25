package com.metsakurr.beonboardingproject.domain.survey.repository;

import com.metsakurr.beonboardingproject.domain.survey.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, Long>, SurveyCustomRepository {
}
