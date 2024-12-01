package com.ic.surveyapi.answer.controller.dto

import survey.type.ItemType

// TODO - 클래스 네이밍을 다시 고려 필요, 벨리데이션 처리가 필요
data class SurveyAnswerRequest(
    val surveyItems: List<SurveyItem>,
) {
    data class SurveyItem(
        val name: String,
        val type: ItemType,
        val answer: String? = null,
        val selectedOptions: List<String> = emptyList(),
    )
}
