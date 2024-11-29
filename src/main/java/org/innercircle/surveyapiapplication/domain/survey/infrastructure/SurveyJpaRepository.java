package org.innercircle.surveyapiapplication.domain.survey.infrastructure;

import org.innercircle.surveyapiapplication.domain.survey.entity.SurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyJpaRepository extends JpaRepository<SurveyEntity, Long> {

}
