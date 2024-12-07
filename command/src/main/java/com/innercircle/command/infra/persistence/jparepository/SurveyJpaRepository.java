package com.innercircle.command.infra.persistence.jparepository;

import com.innercircle.command.domain.survey.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyJpaRepository extends JpaRepository<Survey, String> {

}
