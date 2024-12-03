package com.ic.surveyapi.form.controller.dto

import com.ic.surveydata.form.entity.SurveyFormEntity
import survey.extension.LocalDateTimeExtension.toResponseDateTimeFormat

data class SurveyFormCreateResponse(
    val id: String,
    val title: String,
    // TODO - Response 타임 포맷 정하기 !
    val createdDateTime: String,
) {
   companion object {
        fun of(dto: SurveyFormEntity): SurveyFormCreateResponse {
            return SurveyFormCreateResponse(
                id = dto.id,
                title = dto.title,
                createdDateTime = dto.createdAt.toResponseDateTimeFormat(),
            )
        }
   }
}
