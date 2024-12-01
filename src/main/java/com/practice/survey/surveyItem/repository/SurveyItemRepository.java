package com.practice.survey.surveyItem.repository;

import com.practice.survey.surveyItem.model.entity.SurveyItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyItemRepository extends JpaRepository<SurveyItem, Long> {
}
