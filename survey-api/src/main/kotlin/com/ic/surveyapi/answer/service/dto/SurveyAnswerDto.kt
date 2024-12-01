package com.ic.surveyapi.answer.service.dto

import survey.type.ItemType

data class SurveyAnswerDto(
    val surveyItems: List<SurveyItem>,
) {
    data class SurveyItem(
        val name: String,
        val type: ItemType,
        val answer: String? = null,
        val selectedOptions: List<String> = emptyList(),
    )
}
