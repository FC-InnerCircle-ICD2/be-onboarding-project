package org.icd.surveyapi.surveyconsumer.application.dto.request

import org.icd.surveyapi.exception.InvalidSurveyResponseOptionException
import org.icd.surveyapi.exception.MissingRequiredSurveyResponseItemException
import org.icd.surveyapi.exception.NotFoundSurveyItemException
import org.icd.surveyapi.support.utils.extract
import org.icd.surveycore.domain.survey.Survey
import org.icd.surveycore.domain.surveyItem.ItemType
import org.icd.surveycore.domain.surveyItem.SurveyItem
import org.icd.surveycore.domain.surveyItem.SurveyItemOption
import org.icd.surveycore.domain.surveyresponse.*

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

            when (surveyItem.itemType) {
                ItemType.SHORT_ANSWER -> {
                    if (itemRequest.shortResponse.isNullOrBlank()) {
                        throw MissingRequiredSurveyResponseItemException()
                    }
                }

                ItemType.LONG_ANSWER -> {
                    if (itemRequest.longResponse.isNullOrBlank()) {
                        throw MissingRequiredSurveyResponseItemException()
                    }
                }

                ItemType.SINGLE_CHOICE -> {
                    if (itemRequest.singleChoiceOptionId == null) {
                        throw MissingRequiredSurveyResponseItemException()
                    }
                }

                ItemType.MULTIPLE_CHOICE -> {
                    if (itemRequest.multipleChoiceOptionIds.isNullOrEmpty()) {
                        throw MissingRequiredSurveyResponseItemException()
                    }
                }
            }
        }
    }
}

data class PostSurveyResponseItemRequest(
    val itemId: Long,
    val shortResponse: String? = null,
    val longResponse: String? = null,
    val singleChoiceOptionId: Long? = null,
    val multipleChoiceOptionIds: List<Long>? = null
) {
    fun toEntity(
        surveyResponse: SurveyResponse,
        surveyItems: List<SurveyItem>
    ): SurveyResponseItem {
        val surveyItem = surveyItems.find { it.id == itemId } ?: throw NotFoundSurveyItemException()
        val validOptions = surveyItem.getActiveOptions()
        return SurveyResponseItem.of(
            surveyResponse,
            surveyItem.sequence,
            surveyItem.name,
            surveyItem.description,
            surveyItem.itemType,
            shortResponse = shortResponse?.let { ShortResponse(it) },
            longResponse = longResponse?.let { LongResponse(it) },
            singleChoiceResponse = singleChoiceOptionId?.let { createSingleChoiceResponse(it, validOptions) },
            multipleChoiceResponse = multipleChoiceOptionIds?.let { createMultipleChoiceResponse(it, validOptions) }
        )
    }

    private fun createSingleChoiceResponse(
        singleChoiceOptionId: Long,
        validOptions: List<SurveyItemOption>
    ): SingleChoiceResponse {
        return SingleChoiceResponse(
            itemOptionId = singleChoiceOptionId,
            itemOptionName = validOptions.find { option -> option.id == singleChoiceOptionId }?.name
                ?: throw InvalidSurveyResponseOptionException()
        )
    }

    private fun createMultipleChoiceResponse(
        multipleChoiceOptionIds: List<Long>,
        validOptions: List<SurveyItemOption>
    ): MultipleChoiceResponse {
        return MultipleChoiceResponse(multipleChoiceOptionIds.map {
            ChoiceOption(
                itemOptionId = it,
                itemOptionName = validOptions.find { option -> option.id == it }?.name
                    ?: throw InvalidSurveyResponseOptionException()
            )
        })
    }
}