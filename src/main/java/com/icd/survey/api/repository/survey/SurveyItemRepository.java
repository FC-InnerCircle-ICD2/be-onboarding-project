package com.icd.survey.api.repository.survey;

import com.icd.survey.api.entity.survey.Survey;
import com.icd.survey.api.entity.survey.SurveyItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SurveyItemRepository extends JpaRepository<SurveyItem, Long> {

    Optional<List<SurveyItem>> findBySurvey(Survey survey);
    Optional<List<SurveyItem>> findBySurveyAndIsDeletedTrue(Survey survey);

    @Modifying
    @Query("UPDATE SurveyItem si SET si.isDeleted = true WHERE si.survey.surveySeq = :surveySeq")
    void makeAllAsDeletedBySurveySeq(@Param("surveySeq") Long surveySeq);
}
