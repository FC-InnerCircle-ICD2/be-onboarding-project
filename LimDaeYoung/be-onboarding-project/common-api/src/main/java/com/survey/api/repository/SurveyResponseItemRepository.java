package com.survey.api.repository;

import com.survey.api.entity.SurveyResponseItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyResponseItemRepository extends JpaRepository<SurveyResponseItemEntity, Long> {

}
