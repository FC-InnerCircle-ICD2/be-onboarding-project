package com.ic.surveydata.answer.repository

import com.ic.surveydata.form.entity.SurveyFormEntity
import com.ic.surveydata.form.entity.SurveyItemEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SurveyItemRepository: JpaRepository<SurveyItemEntity, String> {

    // TODO - 다시 쿼리 고민이 필요 ... !!
    @Query("""
        SELECT sf
          FROM SurveyItemEntity si
          JOIN si.surveyFormEntity sf
          JOIN si.surveyAnswers sa
          JOIN sa.surveyAnswerOptions sao
     WHERE sf.id = :surveyFormId
    """)
    fun findBySurveyFormId(@Param("surveyFormId") surveyFormId: String): List<SurveyFormEntity>

}
