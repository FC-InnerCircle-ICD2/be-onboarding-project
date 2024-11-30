package com.ic.surveyapi.form.controller.dto

import kotlinx.datetime.LocalDateTime

data class SurveyFormCreateResponse(
    val id: String,
    val title: String,
    val createdDateTime: LocalDateTime,
)
