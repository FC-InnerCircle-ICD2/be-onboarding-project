package com.ic.surveydata.answer.dto

import com.ic.surveydata.answer.entity.SurveyAnswerEntity

data class SurveyAnswerSearchDto(
    val id: String,
    val surveyItemName: String?,
    val surveyItemDescription: String?,
    val selectedAnswers: List<String>,
)

fun SurveyAnswerEntity.toDtoSurveyAnswerDto(): SurveyAnswerSearchDto {
    return SurveyAnswerSearchDto(
        id = this.id,
        surveyItemName = this.surveyItemEntity?.name,
        surveyItemDescription = this.surveyItemEntity?.description,
        selectedAnswers = this.surveyAnswerOptions.map { it.answer },
    )
}
