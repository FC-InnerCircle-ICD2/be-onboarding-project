package org.survey.db.surveyitem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.survey.db.BaseStatus;

import java.util.List;

public interface SurveyItemRepository extends JpaRepository<SurveyItemEntity, Long> {
    List<SurveyItemEntity> findAllBySurveyIdAndStatusOrderByIdAsc(Long surveyId, BaseStatus status);
}
