package com.practice.survey.surveyVersion.repository;

import com.practice.survey.surveyVersion.model.entity.SurveyVersion;
import com.practice.survey.surveymngt.model.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SurveyVersionRepository extends JpaRepository<SurveyVersion, Long> {

    @Query("SELECT MAX(sv.versionNumber) FROM SurveyVersion sv WHERE sv.survey.surveyId = :surveyId")
    int findMaxVersionNumberBySurveyId(@Param("surveyId") Long surveyId);

    SurveyVersion findBySurvey_SurveyIdAndVersionNumber(Long surveyId, int versionNumber);

}
