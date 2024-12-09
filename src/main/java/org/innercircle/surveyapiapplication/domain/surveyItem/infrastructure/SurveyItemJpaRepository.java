package org.innercircle.surveyapiapplication.domain.surveyItem.infrastructure;

import org.innercircle.surveyapiapplication.domain.surveyItem.entity.SurveyItemEntity;
import org.innercircle.surveyapiapplication.domain.surveyItem.entity.id.SurveyItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SurveyItemJpaRepository extends JpaRepository<SurveyItemEntity, SurveyItemId> {

    @Query(value =
        "SELECT s.* " +
        "FROM survey_items s " +
        "JOIN (SELECT id, MAX(version) as max_version " +
        "      FROM survey_items " +
        "      WHERE survey_id = :surveyId " +
        "      GROUP BY id) latest " +
        "ON s.id = latest.id AND s.version = latest.max_version",
        nativeQuery = true)
    List<SurveyItemEntity> findLatestSurveyItemsBySurveyId(Long surveyId);

    @Query(
          "SELECT s FROM SurveyItemEntity s "
        + "WHERE s.surveyItemId.id = :surveyItemId "
        + "AND s.surveyId = :surveyId "
        + "AND s.surveyItemId.version = ("
              + "SELECT MAX(s2.surveyItemId.version) "
              + "FROM SurveyItemEntity s2 "
              + "WHERE s2.surveyId = :surveyId "
              + "AND   s2.surveyItemId.id = :surveyItemId)"
    )
    Optional<SurveyItemEntity> findLatestSurveyItemBySurveyIdAndSurveyItemId(
        @Param("surveyId") Long surveyId,
        @Param("surveyItemId") Long surveyItemId
    );

    Optional<SurveyItemEntity> findBySurveyItemIdAndSurveyId(SurveyItemId surveyItemId, Long surveyId);

    List<SurveyItemEntity> findBySurveyId(Long surveyId);

}
