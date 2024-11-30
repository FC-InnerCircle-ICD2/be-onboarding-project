package com.ic.surveyapi.form.controller.dto

import kotlinx.datetime.LocalDateTime

data class SurveyCreateResponse(
    val uuid: String,
    val title: String,
    val createdDateTime: LocalDateTime,
)
