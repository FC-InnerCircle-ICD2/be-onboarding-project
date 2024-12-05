package org.survey.db.surveyitem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.survey.db.BaseStatus;

import java.util.List;
import java.util.Optional;

public interface SurveyItemRepository extends JpaRepository<SurveyItemEntity, Long> {
    Optional<SurveyItemEntity> findFirstByIdAndSurveyIdAndStatusOrderByIdDesc(
            Long id,
            Long surveyId,
            BaseStatus status);

    Optional<SurveyItemEntity> findFirstBySurveyIdAndNameAndStatusOrderByIdDesc(
            Long surveyId,
            String name,
            BaseStatus status);

    List<SurveyItemEntity> findAllBySurveyIdAndStatusOrderByIdAsc(Long surveyId, BaseStatus status);
}
