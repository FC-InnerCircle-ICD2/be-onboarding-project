package com.ic.surveyapi.form.controller.dto

data class SurveyFormCreateResponse(
    val id: String,
    val title: String,
    // TODO - Response 타임 포맷 정하기 !
    val createdDateTime: String,
)
