package study.crispin.surveyinfra.adaptor.dto.request

import study.crispin.surveyinfra.adaptor.dto.FormDto

data class SurveyRequestItemDto(
    val name: String,
    val description: String,
    val form: FormDto,
    val required: Boolean,
)
