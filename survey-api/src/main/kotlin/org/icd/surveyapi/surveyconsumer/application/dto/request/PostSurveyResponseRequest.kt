package org.icd.surveyapi.surveyconsumer.application.dto.request

import org.icd.surveyapi.exception.InvalidSurveyResponseOptionException
import org.icd.surveyapi.exception.MissingRequiredSurveyResponseItemException
import org.icd.surveyapi.exception.NotFoundSurveyItemException
import org.icd.surveyapi.support.utils.extract
import org.icd.surveycore.domain.survey.Survey
import org.icd.surveycore.domain.surveyItem.ItemType
import org.icd.surveycore.domain.surveyItem.SurveyItem
import org.icd.surveycore.domain.surveyresponse.SurveyResponse
import org.icd.surveycore.domain.surveyresponse.SurveyResponseItem

data class PostSurveyResponseRequest(
    val uuid: String?,
    val items: List<PostSurveyResponseItemRequest>?
) {
    fun toEntity(survey: Survey): SurveyResponse {
        return SurveyResponse.of(
            survey = survey,
            uuid = uuid.extract("uuid"),
        )
    }

    fun validateRequiredItems(surveyItems: List<SurveyItem>) {
        val requiredItemIds = surveyItems.filter { it.isRequired }.map { it.id }.toSet()
        val respondedItemIds = items.extract("items").map { it.itemId }.toSet()

        val missingItemIds = requiredItemIds - respondedItemIds
        if (missingItemIds.isNotEmpty()) {
            throw MissingRequiredSurveyResponseItemException()
        }
    }

    fun validateItemResponses(surveyItems: List<SurveyItem>) {
        items.extract("items").forEach { itemRequest ->
            val surveyItem = surveyItems.firstOrNull { it.id == itemRequest.itemId }
                ?: throw NotFoundSurveyItemException()
            val validOptions = surveyItem.getActiveOptions().map { it.id }

            when (surveyItem.itemType) {
                ItemType.SHORT_ANSWER, ItemType.LONG_ANSWER -> {
                    if (itemRequest.answer.isNullOrBlank()) {
                        throw MissingRequiredSurveyResponseItemException()
                    }
                }

                ItemType.SINGLE_CHOICE -> {
                    if (itemRequest.itemOptionId == null) {
                        throw MissingRequiredSurveyResponseItemException()
                    }
                    if (itemRequest.itemOptionId !in validOptions) {
                        throw InvalidSurveyResponseOptionException()
                    }
                }

                ItemType.MULTIPLE_CHOICE -> {
                    if (itemRequest.itemOptionIds.isNullOrEmpty()) {
                        throw MissingRequiredSurveyResponseItemException()
                    }
                    if (itemRequest.itemOptionIds.any { it !in validOptions }) {
                        throw InvalidSurveyResponseOptionException()
                    }
                }
            }
        }
    }
}

data class PostSurveyResponseItemRequest(
    val itemId: Long,
    val answer: String? = null,
    val itemOptionId: Long? = null,
    val itemOptionIds: List<Long>? = null
) {
    fun toEntity(surveyResponse: SurveyResponse): SurveyResponseItem {
        return SurveyResponseItem.of(
            surveyResponse = surveyResponse,
            answer = answer,
            itemOptionId = itemOptionId,
            itemOptionIds = itemOptionIds
        )
    }
}
