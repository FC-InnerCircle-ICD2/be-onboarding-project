package com.ic.surveydata.form.dto

import com.ic.surveydata.form.entity.SurveyFormEntity
import com.ic.surveydata.form.entity.SurveyFormEntityBuilder
import com.ic.surveydata.form.entity.SurveyItemEntity
import com.ic.surveydata.form.entity.SurveyOptionEntity
import survey.type.ItemType
import survey.util.UuidGeneratorUtil

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
    val surveyFormEntity = SurveyFormEntity(
        id = UuidGeneratorUtil.generateUuid(),
        title = this.title,
        description = this.description,
        version = version,
    )

    val surveyItemEntities = this.surveyItems.map { requestSurveyItem ->
        SurveyItemEntity(
            id = UuidGeneratorUtil.generateUuid(),
            name = requestSurveyItem.name,
            type = requestSurveyItem.type,
            description = requestSurveyItem.description,
            isRequired = requestSurveyItem.isRequired,
        ).apply {
            requestSurveyItem.options.map { requestOption ->
                SurveyOptionEntity(
                    id = UuidGeneratorUtil.generateUuid(),
                    name = requestOption.name,
                )
            }.let { option ->
                this.addSurveyOption(option)
            }
        }
    }

    surveyFormEntity.addSurveyItems(surveyItemEntities)
    return surveyFormEntity
//    SurveyFormEntity(
//        id = UuidGeneratorUtil.generateUuid(),
//        title = this.title,
//        description = this.description,
//        version = version,
//    ).let {
//        this.surveyItems.map { requestSurveyItem ->
//            SurveyItemEntity(
//                id = UuidGeneratorUtil.generateUuid(),
//                name = requestSurveyItem.name,
//                type = requestSurveyItem.type,
//                description = requestSurveyItem.description,
//                isRequired = requestSurveyItem.isRequired,
//            ).apply {
//                requestSurveyItem.options.map { requestOption ->
//                    SurveyOptionEntity(
//                        id = UuidGeneratorUtil.generateUuid(),
//                        name = requestOption.name,
//                    )
//                }.let { option ->
//                    this.addSurveyOption(option)
//                }
//            }
//        }.apply {
//            it.addSurveyItems(this)
//        }
//    }
//
//    return surveyFormEntity
}
//    return SurveyFormEntityBuilder()
//        .title(this.title)
//        .description(this.description)
//        .version(version)
//        .build()
//    return SurveyFormEntity(
//        id = UuidGeneratorUtil.generateUuid(),
//        title = this.title,
//        description = this.description,
//        version = version,
//        surveyItems = this.surveyItems.map {
//            SurveyItemEntity(
//                id = UuidGeneratorUtil.generateUuid(),
//                name = it.name,
//                description = it.description,
//                isRequired = it.isRequired,
//                type = it.type,
//                surveyOptions = it.options.map {
//                    SurveyOptionEntity(
//                        id = UuidGeneratorUtil.generateUuid(),
//                        name = it.name,
//                    )
//                }.toSet(),
//            )
//        }
//    )
//    val surveyFormEntity =
//        SurveyFormEntity(
//            id = UuidGeneratorUtil.generateUuid(),
//            title = this.title,
//            description = this.description,
//            version = version,
//        )

//    val surveyItemEntities =
//        this.surveyItems.map { surveyItem ->
//            val surveyItemEntity =
//                SurveyItemEntity(
//                    id = UuidGeneratorUtil.generateUuid(),
//                    surveyFormEntity = surveyFormEntity,
//                    name = surveyItem.name,
//                    description = surveyItem.description,
//                    isRequired = surveyItem.isRequired,
//                    type = surveyItem.type,
//                )
//
//            val surveyOptionEntities =
//                surveyItem.options.map { surveyOption ->
//                    SurveyOptionEntity(
//                        id = UuidGeneratorUtil.generateUuid(),
//                        name = surveyOption.name,
//                        surveyItemEntity = surveyItemEntity,
//                    )
//                }
//
//            surveyItemEntity.surveyOptions.addAll(surveyOptionEntities)
//            surveyItemEntity
//        }
//
//    surveyFormEntity.surveyItems.addAll(surveyItemEntities)
//    return surveyFormEntity
//}
