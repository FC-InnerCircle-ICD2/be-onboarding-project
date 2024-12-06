package com.ic.surveydata.answer.repository

import com.ic.surveydata.answer.entity.SurveyAnswerEntity
import com.ic.surveydata.form.entity.SurveyFormEntity
import com.ic.surveydata.form.entity.SurveyItemEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SurveyItemRepository : JpaRepository<SurveyItemEntity, String> {
    // TODO - 다시 쿼리 고민이 필요 ... !!
    @Query(
        """
        SELECT sf
          FROM SurveyItemEntity si
          JOIN si.surveyFormEntity sf
          JOIN si.surveyAnswers sa
          JOIN sa.surveyAnswerOptions sao
     WHERE sf.id = :surveyFormId
    """,
    )
    fun findBySurveyFormId(
        @Param("surveyFormId") surveyFormId: String,
    ): List<SurveyFormEntity>

    @Query(
        """
        SELECT sf
          FROM SurveyItemEntity si
          JOIN si.surveyFormEntity sf
          JOIN si.surveyAnswers sa
          JOIN sa.surveyAnswerOptions sao
     WHERE sf.title = :surveyTitle
    """,
    )
    fun findBySurveyTitle(
        @Param("surveyTitle") surveyTitle: String,
    ): List<SurveyFormEntity>

    // surveyItemName과 surveyItemAnswer를 검색 조건으로 정의
    @Query(
        """
        SELECT sa 
        FROM SurveyAnswerEntity sa
        JOIN sa.surveyItemEntity si
        WHERE (:surveyItemName IS NULL OR si.name = :surveyItemName)
        AND (:surveyItemAnswer IS NULL OR EXISTS (
            SELECT 1 FROM sa.surveyAnswerOptions sao WHERE sao.answer = :surveyItemAnswer
        ))
    """,
    )
    fun findBySurveyItemNameAndAnswer(
        @Param("surveyItemName") surveyItemName: String?,
        @Param("surveyItemAnswer") surveyItemAnswer: String?,
    ): List<SurveyAnswerEntity>
}
