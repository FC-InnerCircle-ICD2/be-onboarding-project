package com.survey.api.repository;

import com.survey.api.dto.SurveyResponseDto;
import com.survey.api.entity.SurveyResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface SurveyResponseRepository extends JpaRepository<SurveyResponseEntity, Long> {
    @Query("SELECT new com.survey.api.dto.SurveyResponseDto(r.id, s.name, s.description, r.regDtm, s.useYn) " +
            "FROM surveyResponse r " +
            "INNER JOIN r.survey s " +
            "WHERE s.id = :id")
    Page<SurveyResponseDto> findResponsesBySurveyIdWithFilters(
        @Param("id") Long id, Pageable pageable
    );

}
