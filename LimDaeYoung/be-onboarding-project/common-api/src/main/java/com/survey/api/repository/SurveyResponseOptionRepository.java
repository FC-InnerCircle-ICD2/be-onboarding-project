package com.survey.api.repository;

import com.survey.api.dto.SurveyResponseItemDto;
import com.survey.api.entity.SurveyResponseOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyResponseOptionRepository extends JpaRepository<SurveyResponseOptionEntity, Long> {
    @Query(value = "SELECT STRING_AGG(r.option_name , ', ') " +
            "FROM survey_response_option s " +
            "INNER JOIN survey_option r on s.option_id = r.id " +
            "WHERE s.response_item_id = :id",
            nativeQuery = true)
    String findResponseOptionByFilters(
            @Param("id") Long id
    );
}
