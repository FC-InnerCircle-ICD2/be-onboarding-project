package study.crispin.surveyinfra.adaptor.dto.response

import study.crispin.surveyinfra.adaptor.dto.FormDto

data class SurveyResponseItemDto(
    val name: String,
    val description: String,
    val form: FormDto,
    val required: Boolean,
    val version: Int,
)
