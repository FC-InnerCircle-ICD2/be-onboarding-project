package com.survey.api.repository;

import com.survey.api.entity.SurveyEntity;
import com.survey.api.entity.SurveyOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyOptionRepository extends JpaRepository<SurveyOptionEntity, Long> {

}
