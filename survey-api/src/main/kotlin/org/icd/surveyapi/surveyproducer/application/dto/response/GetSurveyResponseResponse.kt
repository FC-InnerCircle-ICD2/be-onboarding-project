package org.icd.surveyapi.surveyproducer.application.dto.response

import org.icd.surveycore.domain.surveyItem.ItemType
import org.icd.surveycore.domain.surveyresponse.SurveyResponse
import org.icd.surveycore.domain.surveyresponse.SurveyResponseItem
import java.time.OffsetDateTime


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