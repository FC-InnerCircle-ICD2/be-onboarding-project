package com.innercircle.query.infra.persistence.jparepository;

import com.innercircle.query.infra.persistence.model.survey.response.SurveyResponse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyResponseJpaRepository extends JpaRepository<SurveyResponse, String> {

	List<SurveyResponse> findAllBySurveyId(String surveyId);
}
