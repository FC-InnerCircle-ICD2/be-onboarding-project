package org.survey.domain.service.surveybase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.survey.domain.service.BaseStatus;

import java.util.List;
import java.util.Optional;

public interface SurveyBaseRepository extends JpaRepository<SurveyBaseEntity, Long> {

    Optional<SurveyBaseEntity> findFirstByIdAndStatusOrderByIdDesc(Long id, BaseStatus status);
    List<SurveyBaseEntity> findAllByStatusOrderByIdDesc(BaseStatus status);
}
