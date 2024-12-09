package com.icd.survey.api.entity.survey.repository;

import com.icd.survey.api.entity.survey.SurveyItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SurveyItemRepository extends JpaRepository<SurveyItem, Long> {
    Optional<List<SurveyItem>> findAllBySurveySeq(Long surveySeq);

}
