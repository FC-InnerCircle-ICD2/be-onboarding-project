package com.icd.survey.api.repository.survey;

import com.icd.survey.api.entity.survey.SurveyItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface SurveyItemRepository extends JpaRepository<SurveyItem, Long> {
}
