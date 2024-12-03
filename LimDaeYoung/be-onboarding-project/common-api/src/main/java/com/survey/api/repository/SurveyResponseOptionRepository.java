package com.survey.api.repository;

import com.survey.api.entity.SurveyResponseOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyResponseOptionRepository extends JpaRepository<SurveyResponseOptionEntity, Long> {
    List<SurveyResponseOptionEntity> findByresponseItemId(Long id);
}
