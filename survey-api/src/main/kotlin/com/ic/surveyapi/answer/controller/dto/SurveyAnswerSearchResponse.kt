package com.ic.surveyapi.answer.controller.dto

data class SurveyAnswerSearchResponse(
    val id: String,
    val surveyItemName: String?,
    val surveyItemDescription: String?,
    val selectedAnswers: List<String>
)
