package com.survey.api.repository;

import com.survey.api.dto.SurveyResponseItemDto;
import com.survey.api.entity.SurveyResponseItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SurveyResponseItemRepository extends JpaRepository<SurveyResponseItemEntity, Long> {
    @Query("SELECT new com.survey.api.dto.SurveyResponseItemDto(r.id, s.itemName, s.description, COALESCE(NULLIF(r.answerText, ''), STRING_AGG(o2.optionName , ', ')), s.itemType, s.required, r.regDtm, s.useYn) " +
                "FROM surveyResponseItem r " +
                "INNER JOIN r.surveyItem s on s.itemName like CONCAT('%', :searchParam, '%') " +
                "LEFT JOIN r.reponseOption o1 " +
                "INNER JOIN o1.surveyOtion o2 " +
                "WHERE r.response.id = :id " +
                "GROUP BY r.id, s.itemName, s.description, r.answerText, s.itemType, s.required, r.regDtm, s.useYn " )
    List<SurveyResponseItemDto> findResponseItemByFilters(
            @Param("id") Long id, @Param("searchParam") String searchParam
    );
}
