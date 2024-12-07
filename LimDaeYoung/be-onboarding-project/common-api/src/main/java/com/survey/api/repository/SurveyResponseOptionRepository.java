package com.survey.api.repository;

import com.survey.api.entity.SurveyResponseOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyResponseOptionRepository extends JpaRepository<SurveyResponseOptionEntity, Long> {
    @Query("SELECT o FROM surveyResponseOption o WHERE o.id = :id AND o.optionSnapShotName LIKE %:searchParam%")
    List<SurveyResponseOptionEntity> findByResponseItemIdAndOptionNameLike(
            @Param("id") Long id,
            @Param("searchParam") String searchParam
    );
}
