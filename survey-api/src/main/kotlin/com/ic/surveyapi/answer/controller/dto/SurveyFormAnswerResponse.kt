package com.ic.surveyapi.answer.controller.dto

import com.ic.surveydata.answer.entity.SurveyAnswerEntity
import com.ic.surveydata.answer.entity.SurveyAnswerOptionEntity
import com.ic.surveydata.form.entity.SurveyFormEntity
import com.ic.surveydata.form.entity.SurveyItemEntity
import com.ic.surveydata.form.entity.SurveyOptionEntity

data class SurveyFormAnswerResponse(
    val surveyFormId: String,
    val version: Int,
    val surveyItems: List<SurveyItemDto>,
) {
    data class SurveyItemDto(
        val id: String,
        val name: String,
        val isRequired: Boolean,
        val description: String,
        val type: String,
        val surveyOptions: List<SurveyOptionDto>,
        val surveyAnswers: List<SurveyAnswerDto>,
    )

    data class SurveyOptionDto(
        val id: String,
        val name: String,
    )

    data class SurveyAnswerDto(
        val id: String,
        val surveyAnswerOptions: List<SurveyAnswerOptionDto>,
    )

    data class SurveyAnswerOptionDto(
        val id: String,
        val answer: String,
    )
}

fun SurveyFormEntity.toDto(): SurveyFormAnswerResponse {
    return SurveyFormAnswerResponse(
        surveyFormId = this.id,
        version = this.version,
        surveyItems = this.surveyItems.map { it.toDto() },
    )
}

fun SurveyItemEntity.toDto(): SurveyFormAnswerResponse.SurveyItemDto {
    return SurveyFormAnswerResponse.SurveyItemDto(
        id = this.id,
        name = this.name,
        isRequired = this.isRequired,
        description = this.description,
        type = this.type.name,
        surveyOptions = this.surveyOptions.map { it.toDto() },
        surveyAnswers = this.surveyAnswers.map { it.toDto() },
    )
}

fun SurveyOptionEntity.toDto(): SurveyFormAnswerResponse.SurveyOptionDto {
    return SurveyFormAnswerResponse.SurveyOptionDto(
        id = this.id,
        name = this.name,
    )
}

fun SurveyAnswerEntity.toDto(): SurveyFormAnswerResponse.SurveyAnswerDto {
    return SurveyFormAnswerResponse.SurveyAnswerDto(
        id = this.id,
        surveyAnswerOptions = this.surveyAnswerOptions.map { it.toDto() },
    )
}

fun SurveyAnswerOptionEntity.toDto(): SurveyFormAnswerResponse.SurveyAnswerOptionDto {
    return SurveyFormAnswerResponse.SurveyAnswerOptionDto(
        id = this.id,
        answer = this.answer,
    )
}
