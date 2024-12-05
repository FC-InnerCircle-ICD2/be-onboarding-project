package com.ic.surveydata.form.dto

import com.ic.surveydata.form.entity.SurveyFormEntity
import com.ic.surveydata.form.entity.SurveyItemEntity
import com.ic.surveydata.form.entity.SurveyOptionEntity
import survey.type.ItemType

data class SurveyFormDto(
    val entityId: String,
    val title: String,
    val description: String,
    val version: Int,
    val surveyItems: List<SurveyItemDto>,
) {
    data class SurveyItemDto(
        val itemId: String,
        val name: String,
        val type: ItemType,
        val description: String,
        val isRequired: Boolean,
        val options: List<SurveyOptionDto>,
    ) {
        companion object {
            fun of(surveyItemEntity: SurveyItemEntity): SurveyItemDto {
                return SurveyItemDto(
                    itemId = surveyItemEntity.id,
                    name = surveyItemEntity.name,
                    type = surveyItemEntity.type,
                    description = surveyItemEntity.description,
                    isRequired = surveyItemEntity.isRequired,
                    options = surveyItemEntity.surveyOptions.map { SurveyOptionDto.of(surveyOptionEntity = it) },
                )
            }
        }
    }

    data class SurveyOptionDto(
        val optionId: String,
        val name: String,
    ) {
        companion object {
            fun of(surveyOptionEntity: SurveyOptionEntity): SurveyOptionDto {
                return SurveyOptionDto(
                    optionId = surveyOptionEntity.id,
                    name = surveyOptionEntity.name,
                )
            }
        }
    }

    companion object {
        fun of(surveyFormEntity: SurveyFormEntity): SurveyFormDto {
            return SurveyFormDto(
                entityId = surveyFormEntity.id,
                title = surveyFormEntity.title,
                description = surveyFormEntity.description,
                version = surveyFormEntity.version,
                surveyItems = surveyFormEntity.surveyItems.map { SurveyItemDto.of(surveyItemEntity = it) },
            )
        }
    }
}
