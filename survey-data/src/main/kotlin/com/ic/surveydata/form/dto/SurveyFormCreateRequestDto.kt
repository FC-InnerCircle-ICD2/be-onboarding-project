package com.ic.surveydata.form.dto

import com.ic.surveydata.form.entity.SurveyFormEntity
import com.ic.surveydata.form.entity.SurveyItemEntity
import com.ic.surveydata.form.entity.SurveyOptionEntity
import survey.type.ItemType

data class SurveyFormCreateRequestDto(
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

// TODO - 정리가 필요, 날라가는 쿼리 확인이 필요
fun SurveyFormCreateRequestDto.toEntity(version: Int): SurveyFormEntity {
    val surveyFormEntity =
        SurveyFormEntity.of(dto = this, version = version)

    val surveyItemEntities =
        this.surveyItems.map { requestSurveyItem ->
            SurveyItemEntity.of(dto = requestSurveyItem)
                .apply {
                    requestSurveyItem.options.map { requestOption ->
                        SurveyOptionEntity.of(dto = requestOption)
                    }.let { option ->
                        this.addSurveyOption(option)
                    }
                }
        }

    surveyFormEntity.addSurveyItems(surveyItemEntities)
    return surveyFormEntity
}
