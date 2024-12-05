package org.icd.surveyapi.surveyproducer.application.dto.response

import org.icd.surveycore.domain.survey.Survey
import org.icd.surveycore.domain.surveyItem.ItemType
import org.icd.surveycore.domain.surveyItem.SurveyItem
import org.icd.surveycore.domain.surveyresponse.SurveyResponse
import org.icd.surveycore.domain.surveyresponse.SurveyResponseItem
import java.time.OffsetDateTime

data class GetSurveyResponse(
    val id: Long,
    val name: String,
    val description: String?,
    val items: List<GetSurveyItemResponse>,
    val responses: List<GetSurveyResponseResponse>
) {
    constructor(survey: Survey) : this(
        id = survey.id,
        name = survey.name,
        description = survey.description,
        items = survey.items
            .map { GetSurveyItemResponse(it) }
            .sortedWith(compareBy({ !it.isActive }, { it.sequence })),
        responses = survey.responses.map { GetSurveyResponseResponse(it) }
    )
}

data class GetSurveyItemResponse(
    val id: Long,
    val isActive: Boolean,
    val sequence: Int,
    val name: String,
    val description: String?,
    val itemType: ItemType,
    val options: List<GetSurveyItemOptionResponse> = listOf(),
    val isRequired: Boolean,
) {
    constructor(surveyItem: SurveyItem) : this(
        id = surveyItem.id,
        isActive = surveyItem.isActive,
        sequence = surveyItem.sequence,
        name = surveyItem.name,
        description = surveyItem.description,
        itemType = surveyItem.itemType,
        options = surveyItem.options
            .map { GetSurveyItemOptionResponse(it.id, it.name, it.isActive) }
            .sortedWith(compareBy({ !it.isActive }, { it.id })),
        isRequired = surveyItem.isRequired
    )
}

data class GetSurveyItemOptionResponse(
    val id: Long,
    val name: String,
    val isActive: Boolean
)

data class GetSurveyResponseResponse(
    val id: Long,
    val createdAt: OffsetDateTime?,
    val items: List<GetSurveyResponseItemResponse>
) {
    constructor(surveyResponse: SurveyResponse) : this(
        id = surveyResponse.id,
        createdAt = surveyResponse.createdAt,
        items = surveyResponse.items.map { GetSurveyResponseItemResponse(it) }
    )
}

data class GetSurveyResponseItemResponse(
    val sequence: Int,
    val name: String,
    val description: String?,
    val itemType: ItemType,
    val shortResponse: String?,
    val longResponse: String?,
    val singleChoiceResponse: GetSurveyResponseItemOptionResponse?,
    val multipleChoiceResponse: List<GetSurveyResponseItemOptionResponse>?
) {
    constructor(surveyResponseItem: SurveyResponseItem) : this(
        sequence = surveyResponseItem.sequence,
        name = surveyResponseItem.name,
        description = surveyResponseItem.description,
        itemType = surveyResponseItem.itemType,
        shortResponse = surveyResponseItem.shortResponse?.shortResponse,
        longResponse = surveyResponseItem.longResponse?.longResponse,
        singleChoiceResponse = surveyResponseItem.singleChoiceResponse?.let {
            GetSurveyResponseItemOptionResponse(
                it.itemOptionId,
                it.itemOptionName
            )
        },
        multipleChoiceResponse = surveyResponseItem.multipleChoiceResponse?.let {
            it.choiceOptions.map { option ->
                GetSurveyResponseItemOptionResponse(
                    option.itemOptionId,
                    option.itemOptionName
                )
            }
        }
    )
}

data class GetSurveyResponseItemOptionResponse(
    val itemOptionId: Long?,
    val itemOptionName: String?
)
