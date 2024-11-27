package study.crispin.surveyinfra.extension

import java.util.UUID
import study.crispin.surveyinfra.adaptor.dto.FormDto
import study.crispin.surveyinfra.adaptor.dto.SurveyDto
import study.crispin.surveyinfra.adaptor.dto.SurveyItemDto
import study.crispin.surveyinfra.port.SaveSurveyPort
import study.crispin.surveyinfra.repository.entity.FormEmbeddable
import study.crispin.surveyinfra.repository.entity.FormType
import study.crispin.surveyinfra.repository.entity.SurveyEntity
import study.crispin.surveyinfra.repository.entity.SurveyItemEntity

fun SaveSurveyPort.Request.toEntity(): SurveyEntity = SurveyEntity(
    name = this.name,
    description = this.description,
)

fun SurveyItemDto.toEntity(surveyId: UUID): SurveyItemEntity = SurveyItemEntity(
    surveyId = surveyId,
    name = this.name,
    description = this.description,
    form = this.form.toEmbeddable(),
    required = this.required,
)

fun FormDto.toEmbeddable(): FormEmbeddable = when (this) {
    FormDto.LongInput -> FormEmbeddable(type = FormType.LONG_INPUT)
    FormDto.ShortInput -> FormEmbeddable(type = FormType.SHORT_INPUT)

    is FormDto.MultiSelect -> FormEmbeddable(
        type = FormType.MULTI_SELECT,
        options = this.options,
    )

    is FormDto.SingleSelect -> FormEmbeddable(
        type = FormType.SINGLE_SELECT,
        options = this.options,
    )
}

fun SurveyEntity.toDto(): SurveyDto = SurveyDto(
    id = this.id!!,
    name = this.name,
    description = this.description,
)

fun SurveyItemEntity.toDto(): SurveyItemDto = SurveyItemDto(
    name = this.name,
    description = this.description,
    form = this.form.toDto(),
    required = this.required,
)

fun FormEmbeddable.toDto(): FormDto = when (this.type) {
    FormType.LONG_INPUT -> FormDto.LongInput
    FormType.SHORT_INPUT -> FormDto.ShortInput
    FormType.MULTI_SELECT -> FormDto.MultiSelect(this.options)
    FormType.SINGLE_SELECT -> FormDto.SingleSelect(this.options)
}
