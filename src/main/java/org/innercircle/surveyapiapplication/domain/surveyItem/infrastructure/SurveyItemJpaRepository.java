package org.innercircle.surveyapiapplication.domain.surveyItem.infrastructure;

import org.innercircle.surveyapiapplication.domain.surveyItem.entity.SurveyItemEntity;
import org.innercircle.surveyapiapplication.domain.surveyItem.entity.id.SurveyItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SurveyItemJpaRepository extends JpaRepository<SurveyItemEntity, SurveyItemId> {

    List<SurveyItemEntity> findBySurveyId(Long surveyId);

    @Query(
          "SELECT s FROM SurveyItemEntity s "
        + "WHERE s.surveyItemId.id = :id "
        + "AND s.surveyItemId.version = ("
              + "SELECT MAX(s2.surveyItemId.version) "
              + "FROM SurveyItemEntity s2 "
              + "WHERE s2.surveyItemId.id = :id"
        + ")"
    )
    Optional<SurveyItemEntity> findLatestQuestionById(@Param("id") Long id);

}
