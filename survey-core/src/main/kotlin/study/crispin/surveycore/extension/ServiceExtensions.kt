package study.crispin.surveycore.extension

import study.crispin.surveycore.domain.Form
import study.crispin.surveycore.domain.Survey
import study.crispin.surveycore.domain.SurveyItem
import study.crispin.surveyinfra.adaptor.dto.FormDto
import study.crispin.surveyinfra.adaptor.dto.SurveyDto
import study.crispin.surveyinfra.adaptor.dto.SurveyItemDto

fun SurveyItem.toDto() = SurveyItemDto(
    name = this.name,
    description = this.description,
    form = this.form.toDto(),
    required = this.required,
)

fun Form.toDto() = when (this) {
    Form.LongInput -> FormDto.LongInput
    is Form.MultiSelect -> FormDto.MultiSelect(this.options)
    Form.ShortInput -> FormDto.ShortInput
    is Form.SingleSelect -> FormDto.SingleSelect(this.options)
}

fun SurveyDto.toDomain() = Survey(
    id = this.id,
    name = this.name,
    description = this.description,
    items = this.items.map { it.toDomain() }
)

fun SurveyItemDto.toDomain() = SurveyItem(
    name = this.name,
    description = this.description,
    form = this.form.toDomain(),
    required = this.required,
)

fun FormDto.toDomain() = when (this) {
    FormDto.LongInput -> Form.LongInput
    is FormDto.MultiSelect -> Form.MultiSelect(this.options)
    FormDto.ShortInput -> Form.ShortInput
    is FormDto.SingleSelect -> Form.SingleSelect(this.options)
}
