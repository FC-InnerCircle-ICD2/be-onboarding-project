package com.innercircle.query.infra.persistence.jparepository;

import com.innercircle.query.infra.persistence.model.survey.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyJpaRepository extends JpaRepository<Survey, String> {

}
