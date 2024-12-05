package com.ic.surveydata.answer.dto

import com.ic.surveydata.answer.entity.SurveyAnswerEntity
import com.ic.surveydata.answer.entity.SurveyAnswerOptionEntity
import com.ic.surveydata.form.entity.SurveyFormEntity
import survey.type.ItemType
import survey.util.UuidGeneratorUtil

data class SurveyAnswerDto(
    val surveyItems: List<SurveyItem>,
) {
    data class SurveyItem(
        val name: String,
        val type: ItemType,
        val answer: String? = null,
        val selectedOptions: List<String> = emptyList(),
    )
}

// TODO - 코드 정리가 필요
fun SurveyAnswerDto.toEntity(surveyFormEntity: SurveyFormEntity): List<SurveyAnswerEntity> =
    this.surveyItems.map { surveyItem ->
        val surveyItemEntity =
            surveyFormEntity.surveyItems.find { surveyElement -> surveyElement.name == surveyItem.name }
        val surveyAnswerEntity =
            SurveyAnswerEntity(
                id = UuidGeneratorUtil.generateUuid(),
                surveyItemEntity = surveyItemEntity,
            )

        val surveyItemType = surveyItem.type
        val surveyAnswerOptionEntities =
            when (surveyItemType) {
                ItemType.SHORT_ANSWER, ItemType.LONG_ANSWER ->
                    listOf(
                        SurveyAnswerOptionEntity(
                            id = UuidGeneratorUtil.generateUuid(),
                            answer = surveyItem.answer!!,
                            surveyAnswerEntity = surveyAnswerEntity, // 연관 설정 추가
                        ),
                    )

                else ->
                    surveyItem.selectedOptions.map { answer ->
                        SurveyAnswerOptionEntity(
                            id = UuidGeneratorUtil.generateUuid(),
                            answer = answer,
                            surveyAnswerEntity = surveyAnswerEntity, // 연관 설정 추가
                        )
                    }
            }

        surveyAnswerEntity.surveyAnswerOptions.addAll(surveyAnswerOptionEntities)
        surveyAnswerEntity
    }
