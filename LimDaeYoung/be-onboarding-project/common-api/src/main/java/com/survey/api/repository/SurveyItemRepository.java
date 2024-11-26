package com.survey.api.repository;

import com.survey.api.entity.SurveyItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyItemRepository extends JpaRepository<SurveyItemEntity, Long> {

}
