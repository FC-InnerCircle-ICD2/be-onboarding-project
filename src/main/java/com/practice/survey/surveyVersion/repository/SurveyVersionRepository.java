package com.practice.survey.surveyVersion.repository;

import com.practice.survey.surveyVersion.model.entity.SurveyVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SurveyVersionRepository extends JpaRepository<SurveyVersion, Long> {
//    @Query("SELECT MAX(sv.versionNumber) FROM SurveyVersion sv WHERE sv.survey.surveyId = :surveyId")
//    int findMaxVersionNumberBySurveyId(@Param("surveyId") Long surveyId);

    @Query("SELECT sv.versionId FROM SurveyVersion sv WHERE sv.survey.surveyId = :surveyId")
    Long findVersionIdBySurveyId(@Param("surveyId") Long surveyId);
}
