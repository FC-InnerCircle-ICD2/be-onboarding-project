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
        items = survey.items.map { GetSurveyItemResponse(it) }
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
        options = surveyItem.options.map { GetSurveyItemOptionResponse(it.id, it.name, it.isActive) },
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
    val itemId: Long,
    val answer: String?,
    val itemOption: GetSurveyResponseItemOptionResponse?,
    val itemOptions: List<GetSurveyResponseItemOptionResponse>
) {
    constructor(surveyResponseItem: SurveyResponseItem) : this(
        itemId = surveyResponseItem.surveyItemId,
        answer = surveyResponseItem.answer,
        itemOption = surveyResponseItem.itemOptionId?.let { itemOptionId ->
            GetSurveyResponseItemOptionResponse(
                itemOptionId = itemOptionId,
                itemOptionName = surveyResponseItem.surveyResponse.survey.getOptionNameById(itemOptionId)
            )
        },
        itemOptions = surveyResponseItem.itemOptionIds?.map { itemOptionId ->
            GetSurveyResponseItemOptionResponse(
                itemOptionId = itemOptionId,
                itemOptionName = surveyResponseItem.surveyResponse.survey.getOptionNameById(itemOptionId)
            )
        } ?: listOf()
    )
}

data class GetSurveyResponseItemOptionResponse(
    val itemOptionId: Long?,
    val itemOptionName: String?
)

fun Survey.getOptionNameById(optionId: Long): String? {
    return this.items
        .flatMap { it.options }
        .firstOrNull { option -> option.id == optionId }
        ?.name
}