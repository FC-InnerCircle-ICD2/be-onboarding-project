package com.innercircle.command.infra.persistence.jparepository;

import com.innercircle.command.domain.survey.Survey;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyJpaRepository extends JpaRepository<Survey, UUID> {

}
