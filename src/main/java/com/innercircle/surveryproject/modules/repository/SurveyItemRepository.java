package com.innercircle.surveryproject.modules.repository;

import com.innercircle.surveryproject.modules.entity.SurveyItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SurveyItemRepository extends JpaRepository<SurveyItem, Long> {

    Optional<SurveyItem> findBySurvey_IdAndId(Long surveyId, Long id);

}
