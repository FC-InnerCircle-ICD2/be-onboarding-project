package com.innercircle.command.infra.persistence.jparepository;

import com.innercircle.command.domain.survey.Survey;
import org.springframework.data.repository.CrudRepository;

public interface SurveyJpaRepository extends CrudRepository<Survey, String> {

}
