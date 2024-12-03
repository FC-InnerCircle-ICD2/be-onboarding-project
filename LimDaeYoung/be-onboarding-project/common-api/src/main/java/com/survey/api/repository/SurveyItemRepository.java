package com.survey.api.repository;

import com.survey.api.entity.SurveyEntity;
import com.survey.api.entity.SurveyItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SurveyItemRepository extends JpaRepository<SurveyItemEntity, Long> {
    public long countBySurveyAndUseYn(SurveyEntity survey, String useYn);
    public SurveyItemEntity findByIdAndItemTypeAndSurvey(long id, String itemType, SurveyEntity survey);
    public SurveyItemEntity findItemById(long id);

}
