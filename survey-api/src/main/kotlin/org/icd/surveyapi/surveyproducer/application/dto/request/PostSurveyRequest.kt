package org.icd.surveyapi.surveyproducer.application.dto.request

import org.icd.surveyapi.support.utils.extract
import org.icd.surveycore.domain.survey.Survey
import org.icd.surveycore.domain.surveyItem.ItemType
import org.icd.surveycore.domain.surveyItem.SurveyItem
import org.icd.surveycore.domain.surveyItem.SurveyItemOption

data class PostSurveyRequest(
    val name: String?,
    val description: String?,
    val items: List<PostSurveyItemRequest>?
) {
    fun toSurvey(): Survey {
        return Survey.of(
            name = name.extract("name"),
            description = description,
        )
    }
}

data class PostSurveyItemRequest(
    val sequence: Int?,
    val name: String?,
    val description: String? = null,
    val itemType: ItemType?,
    val options: List<String>? = null,
) {
    fun toSurveyItem(survey: Survey): SurveyItem {
        return SurveyItem.of(
            survey = survey,
            sequence = sequence.extract("sequence"),
            name = name.extract("name"),
            description = description,
            itemType = itemType.extract("itemType"),
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
