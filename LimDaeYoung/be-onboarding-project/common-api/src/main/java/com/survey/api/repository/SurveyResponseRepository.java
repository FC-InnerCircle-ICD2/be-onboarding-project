package com.survey.api.repository;

import com.survey.api.dto.SurveyResponseDto;
import com.survey.api.entity.SurveyResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyResponseRepository extends JpaRepository<SurveyResponseEntity, Long> {
    @Query(value = "SELECT r " +
            "FROM surveyResponse r " +
            "join fetch r.responseItems s " +
            "WHERE r.surveyId = :id",
        countQuery = "SELECT COUNT(r) FROM surveyResponse r WHERE r.surveyId = :id")
    List<SurveyResponseEntity> findResponsesBySurveyIdWithFilters(
        @Param("id") Long id
            , Pageable pageable
    );
}
