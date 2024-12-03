package com.ic.surveyapi.form.controller.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import survey.type.ItemType

data class SurveyFormCreateRequest(
    @field:NotBlank
    val title: String,
    @field:NotBlank
    val description: String,
    @field:Size(min = 1, max = 10, message = "유효한 항목 갯수는 1개에서 10개 사이 입니다.")
    val surveyItems: List<SurveyItem>,
) {
    data class SurveyItem(
        @field:NotBlank
        val name: String,
        val type: ItemType,
        @field:NotBlank
        val description: String,
        val isRequired: Boolean,
        val options: Set<ItemOption>,
    )

    data class ItemOption(
        val name: String,
    )
}
