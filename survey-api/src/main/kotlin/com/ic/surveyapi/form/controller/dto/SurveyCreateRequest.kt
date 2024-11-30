package com.ic.surveyapi.form.controller.dto

import survey.type.ItemType

data class SurveyCreateRequest(
    val title: String,
    val description: String,
    val surveyItems: List<SurveyItem>,
) {
    // TODO - Mapping 은 Object Mapper 같은 유틸성 클래스를 만들어서 사용이 필요 !
    data class SurveyItem(
        val name: String,
        val type: ItemType,
        val description: String,
        val isRequired: Boolean,
        val options: List<ItemOption>,
    )

    data class ItemOption(
        val name: String,
    )
}
