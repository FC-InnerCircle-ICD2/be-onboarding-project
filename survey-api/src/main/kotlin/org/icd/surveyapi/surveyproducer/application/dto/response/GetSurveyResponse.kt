package org.icd.surveyapi.surveyproducer.application.dto.response

import org.icd.surveycore.domain.survey.Survey
import org.icd.surveycore.domain.surveyItem.ItemType
import org.icd.surveycore.domain.surveyItem.SurveyItem

data class GetSurveyResponse(
    val id: Long,
    val name: String,
    val description: String?,
    val items: List<GetSurveyItemResponse>,
) {
    constructor(survey: Survey) : this(
        id = survey.id,
        name = survey.name,
        description = survey.description,
        items = survey.items.map { GetSurveyItemResponse(it) }
            .sortedWith(compareBy({ !it.isActive }, { it.sequence })),
    )
}

data class GetSurveyItemResponse(
    val id: Long,
    val isActive: Boolean,
    val sequence: Int,
    val name: String,
    val description: String?,
    val itemType: ItemType,
    val options: List<String> = listOf(),
) {
    constructor(surveyItem: SurveyItem) : this(
        id = surveyItem.id,
        isActive = surveyItem.isActive,
        sequence = surveyItem.sequence,
        name = surveyItem.name,
        description = surveyItem.description,
        itemType = surveyItem.itemType,
        options = surveyItem.options.map { it.name },
    )
}
