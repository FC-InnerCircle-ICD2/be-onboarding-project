package com.survey.api.repository;

import com.survey.api.entity.SurveyItemEntity;
import com.survey.api.entity.SurveyOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyOptionRepository extends JpaRepository<SurveyOptionEntity, Long> {
    public boolean existsByIdAndSurveyItem(long id, SurveyItemEntity surveyItem);
}
