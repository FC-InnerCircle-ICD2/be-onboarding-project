package com.ic.surveyapi.form.controller.dto

import survey.type.ItemType

class SurveyFormResponse(
    val entityId: String,
    val title: String,
    val description: String,
    val version: Int,
    val surveyItems: List<SurveyItemDto>,
) {
    data class SurveyItemDto(
        val itemId: String,
        val name: String,
        val type: ItemType,
        val description: String,
        val isRequired: Boolean,
        val options: List<SurveyOptionDto>,
    )

    data class SurveyOptionDto(
        val optionId: String,
        val name: String,
    )
}
