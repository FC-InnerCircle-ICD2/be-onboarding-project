package com.innercicle.adapter.out.persistence.survey.repository;

import com.innercicle.adapter.out.persistence.survey.entity.SurveyItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 설문 항목 레포지토리
 *
 * @author seunggu.lee
 */
public interface SurveyItemRepository extends JpaRepository<SurveyItemEntity, Long> {

}
