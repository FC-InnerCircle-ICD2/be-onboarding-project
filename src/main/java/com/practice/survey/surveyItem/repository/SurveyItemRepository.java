package com.practice.survey.surveyItem.repository;

import com.practice.survey.surveyItem.model.entity.SurveyItem;
import com.practice.survey.surveyVersion.model.entity.SurveyVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveyItemRepository extends JpaRepository<SurveyItem, Long> {

    List<SurveyItem> findByVersion(SurveyVersion surveyVersion);

}
