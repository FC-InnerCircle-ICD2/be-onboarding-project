package com.ic.surveyapi.form.service.dto

data class SurveyCreateRequestDto(
    val title: String,
    val description: String,
    val surveyItems: List<SurveyItem>,
) {
    data class SurveyItem(
        val name: String,
        val type: String,
        val description: String,
        val isRequired: Boolean,
        val options: List<ItemOption>,
    )

    data class ItemOption(
        val name: String,
    )
}
