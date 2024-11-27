package com.icd.survey.api.repository.survey;

import com.icd.survey.api.entity.survey.SurveyItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SurveyItemRepository extends JpaRepository<SurveyItem, Long> {

    Optional<List<SurveyItem>> findAllBySurveySeq(Long surveySeq);
}
