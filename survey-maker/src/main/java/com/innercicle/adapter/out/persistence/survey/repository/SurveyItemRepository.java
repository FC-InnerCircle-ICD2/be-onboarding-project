package com.innercicle.adapter.out.persistence.survey.repository;

import com.innercicle.adapter.out.persistence.survey.entity.SurveyEntity;
import com.innercicle.adapter.out.persistence.survey.entity.SurveyItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 설문 항목 레포지토리
 *
 * @author seunggu.lee
 */
public interface SurveyItemRepository extends JpaRepository<SurveyItemEntity, Long> {

    /**
     * 설문에 해당하는 설문 항목 목록 조회
     *
     * @param surveyEntity 설문 엔티티
     * @return 설문 항목 목록
     */
    List<SurveyItemEntity> findAllBySurvey(SurveyEntity surveyEntity);

}
