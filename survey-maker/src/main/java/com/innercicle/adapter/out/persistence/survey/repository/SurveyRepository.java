package com.innercicle.adapter.out.persistence.survey.repository;

import com.innercicle.adapter.out.persistence.survey.entity.SurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 설문 레포지토리
 *
 * @author seunggu.lee
 */
public interface SurveyRepository extends JpaRepository<SurveyEntity, Long> {

}
