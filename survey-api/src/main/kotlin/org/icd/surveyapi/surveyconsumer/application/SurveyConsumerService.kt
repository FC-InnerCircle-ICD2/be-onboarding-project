package org.icd.surveyapi.surveyconsumer.application

import org.icd.surveyapi.exception.DuplicateSurveyResponseException
import org.icd.surveyapi.exception.MissingRequiredSurveyResponseItemException
import org.icd.surveyapi.exception.NotFoundSurveyException
import org.icd.surveyapi.exception.NotFoundSurveyItemException
import org.icd.surveyapi.support.utils.extract
import org.icd.surveyapi.surveyconsumer.application.dto.request.PostSurveyResponseItemRequest
import org.icd.surveyapi.surveyconsumer.application.dto.request.PostSurveyResponseRequest
import org.icd.surveycore.domain.event.CreateSurveyResponseEvent
import org.icd.surveycore.domain.survey.SurveyRepository
import org.icd.surveycore.domain.surveyItem.ItemType
import org.icd.surveycore.domain.surveyItem.SurveyItem
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class SurveyConsumerService(
    private val surveyRepository: SurveyRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    @Transactional
    fun postSurveyResponse(surveyId: Long, request: PostSurveyResponseRequest) {
        val survey = surveyRepository.findByIdOrNull(surveyId) ?: throw NotFoundSurveyException()
        if (survey.responses.find { it.uuid == request.uuid } != null) {
            throw DuplicateSurveyResponseException()
        }

        val items = request.items.extract("items")
        validateRequiredItems(items, survey.getActiveItems())
        validateItemResponses(items, survey.getActiveItems())

        val surveyResponse = request.toEntity(survey)
        items.map {
            surveyResponse.addItem(it.toEntity(surveyResponse))
        }

        applicationEventPublisher.publishEvent(CreateSurveyResponseEvent(surveyResponse))
    }
    private fun validateRequiredItems(items: List<PostSurveyResponseItemRequest>, surveyItems: List<SurveyItem>) {
        val requiredItemIds = surveyItems.filter { it.isRequired }.map { it.id }.toSet()
        val respondedItemIds = items.map { it.itemId }.toSet()

        val missingItemIds = requiredItemIds - respondedItemIds
        if (missingItemIds.isNotEmpty()) {
            throw MissingRequiredSurveyResponseItemException()
        }
    }
    private fun validateItemResponses(items: List<PostSurveyResponseItemRequest>, surveyItems: List<SurveyItem>) {
        items.forEach { itemRequest ->
            val surveyItem = surveyItems.firstOrNull { it.id == itemRequest.itemId }
                ?: throw NotFoundSurveyItemException()

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
                }

                ItemType.MULTIPLE_CHOICE -> {
                    if (itemRequest.itemOptionIds.isNullOrEmpty()) {
                        throw MissingRequiredSurveyResponseItemException()
                    }
                }
            }
        }
    }
}