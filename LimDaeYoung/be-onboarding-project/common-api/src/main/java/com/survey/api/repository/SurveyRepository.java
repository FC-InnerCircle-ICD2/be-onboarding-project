package com.survey.api.repository;

import com.survey.api.entity.SurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<SurveyEntity, Long> {
    public List<SurveyEntity> findById(String id);

    public List<SurveyEntity> findByName(String name);

    public List<SurveyEntity> findByDescription(String name);
}
