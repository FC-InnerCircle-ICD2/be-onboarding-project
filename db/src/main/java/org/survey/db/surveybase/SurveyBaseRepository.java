package org.survey.db.surveybase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.survey.db.BaseStatus;

import java.util.List;
import java.util.Optional;

public interface SurveyBaseRepository extends JpaRepository<SurveyBaseEntity, Long> {

    Optional<SurveyBaseEntity> findFirstByIdAndStatusOrderByIdDesc(Long id, BaseStatus status);
    List<SurveyBaseEntity> findAllByStatusOrderByIdDesc(BaseStatus status);
}
