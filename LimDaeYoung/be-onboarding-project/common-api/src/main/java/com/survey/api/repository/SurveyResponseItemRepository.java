package com.survey.api.repository;

import com.survey.api.dto.SurveyResponseDto;
import com.survey.api.dto.SurveyResponseItemDto;
import com.survey.api.entity.SurveyResponseItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyResponseItemRepository extends JpaRepository<SurveyResponseItemEntity, Long> {
    @Query("SELECT new com.survey.api.dto.SurveyResponseItemDto(r.id, s.itemName, s.description, r.answerText, s.itemType, s.required, r.regDtm, s.useYn) " +
            "FROM surveyResponseItem r " +
            "INNER JOIN r.surveyItem s " +
            "WHERE r.response.id = :id")
    List<SurveyResponseItemDto> findResponseItemByFilters(
            @Param("id") Long id
    );
}
