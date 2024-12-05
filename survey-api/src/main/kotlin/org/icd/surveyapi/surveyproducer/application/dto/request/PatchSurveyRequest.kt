package org.icd.surveyapi.surveyproducer.application.dto.request

import org.icd.surveyapi.support.utils.extract
import org.icd.surveycore.domain.survey.Survey
import org.icd.surveycore.domain.surveyItem.ItemType
import org.icd.surveycore.domain.surveyItem.SurveyItem
import org.icd.surveycore.domain.surveyItem.SurveyItemOption

data class PatchSurveyRequest(
    val name: String?,
    val description: String?,
    val deleteItems: List<Long>,
    val updateItems: List<UpdateSurveyItemRequest>? = null,
    val createItems: List<CreateSurveyItemRequest>? = null
)

data class UpdateSurveyItemRequest(
    val id: Long?,
    val sequence: Int?,
    val name: String?,
    val description: String? = null,
    val itemType: ItemType?,
    val deleteOptions: List<Long> = emptyList(),
    val updateOptions: List<UpdateSurveyItemOptionRequest> = emptyList(),
    val createOptions: List<String> = emptyList(),
    val isRequired: Boolean?,
) {
    fun toSurveyItemOptions(surveyItem: SurveyItem): List<SurveyItemOption>? {
        if (surveyItem.itemType == ItemType.SHORT_ANSWER || surveyItem.itemType == ItemType.LONG_ANSWER) {
            return null
        }
        return createOptions.map {
            SurveyItemOption.of(surveyItem, it)
        }
    }
}

data class UpdateSurveyItemOptionRequest(
    val id: Long?,
    val name: String?,
)

data class CreateSurveyItemRequest(
    val sequence: Int?,
    val name: String?,
    val description: String? = null,
    val itemType: ItemType?,
    val options: List<String> = emptyList(),
    val isRequired: Boolean?
) {
    fun toSurveyItem(survey: Survey): SurveyItem {
        return SurveyItem.of(
            survey = survey,
            sequence = sequence.extract("sequence"),
            name = name.extract("name"),
            description = description,
            itemType = itemType.extract("itemType"),
            isRequired = isRequired.extract("isRequired")
        )
    }
    fun toSurveyItemOptions(surveyItem: SurveyItem): List<SurveyItemOption>? {
        if (itemType == ItemType.SHORT_ANSWER || itemType == ItemType.LONG_ANSWER) {
            return null
        }
        return options.extract("options").map {
            SurveyItemOption.of(surveyItem, it)
        }
    }
}
