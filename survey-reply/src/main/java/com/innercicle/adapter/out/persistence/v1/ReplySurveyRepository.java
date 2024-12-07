package com.innercicle.adapter.out.persistence.v1;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplySurveyRepository extends JpaRepository<ReplySurveyEntity, Long>, ReplySurveyRepositoryQuerydsl {

}
