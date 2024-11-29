package com.ic.surveyapi.controller.dto

import survey.type.ItemType

data class SurveyCreateRequest(
    val title: String,
    val description: String,
    val surveyItems: List<SurveyItem>,
) {
    data class SurveyItem(
        val name: String,
        val type: ItemType,
        val description: String,
        val isRequired: Boolean,
        val options: List<ItemOption>,
    )

    data class ItemOption(
        val option: String,
    )
}
