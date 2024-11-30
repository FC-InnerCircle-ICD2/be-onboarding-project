package com.survey.api.repository;

import com.survey.api.dto.SurveyResponseItemDto;
import com.survey.api.entity.SurveyResponseOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyResponseOptionRepository extends JpaRepository<SurveyResponseOptionEntity, Long> {

}
