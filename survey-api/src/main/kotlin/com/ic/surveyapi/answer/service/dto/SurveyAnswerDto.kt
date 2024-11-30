package com.ic.surveyapi.answer.service.dto

data class SurveyAnswerDto(
    val surveyItems: List<SurveyItem>,
) {
    data class SurveyItem(
        val name: String,
        val answer: String? = null,
        val selectedOptions: List<String> = emptyList(),
    )
}
