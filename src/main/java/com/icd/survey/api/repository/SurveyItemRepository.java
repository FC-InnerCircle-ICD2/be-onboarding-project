package com.icd.survey.api.repository;

import com.icd.survey.api.entity.SurveyItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyItemRepository extends JpaRepository<SurveyItem, Long> {
}
