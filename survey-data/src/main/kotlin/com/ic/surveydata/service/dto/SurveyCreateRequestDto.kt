package com.ic.surveydata.service.dto

import com.ic.surveydata.entity.form.SurveyFormEntity
import com.ic.surveydata.entity.form.SurveyItemEntity
import com.ic.surveydata.entity.form.SurveyOptionEntity
import survey.type.ItemType
import survey.util.UuidGeneratorUtil

data class SurveyCreateRequestDto(
    val title: String,
    val description: String,
    val surveyItems: List<SurveyItem>,
) {
    data class SurveyItem(
        val name: String,
        val type: ItemType,
        val description: String,
        val isRequired: Boolean,
        val options: List<SurveyOption>,
    )

    data class SurveyOption(
        val name: String,
    )
}

// TODO - 정리가 필요
fun SurveyCreateRequestDto.toEntity(version: Int): SurveyFormEntity {
    val surveyFormEntity =
        SurveyFormEntity(
            id = UuidGeneratorUtil.generateUuid(),
            title = this.title,
            description = this.description,
            version = version,
        )

    val surveyItemEntities =
        this.surveyItems.map { surveyItem ->
            val surveyItemEntity =
                SurveyItemEntity(
                    id = UuidGeneratorUtil.generateUuid(),
                    surveyFormEntity = surveyFormEntity,
                    name = surveyItem.name,
                    description = surveyItem.description,
                    isRequired = surveyItem.isRequired,
                    type = surveyItem.type,
                )

            val surveyOptionEntities =
                surveyItem.options.map { surveyOption ->
                    SurveyOptionEntity(
                        id = UuidGeneratorUtil.generateUuid(),
                        name = surveyOption.name,
                        surveyItemEntity = surveyItemEntity,
                    )
                }

            surveyItemEntity.surveyOptions.addAll(surveyOptionEntities)
            surveyItemEntity
        }

    surveyFormEntity.surveyItems.addAll(surveyItemEntities)
    return surveyFormEntity
}
