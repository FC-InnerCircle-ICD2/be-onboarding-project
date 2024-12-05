package org.icd.surveyapi.surveyconsumer.application

import org.icd.surveyapi.exception.DuplicateSurveyResponseException
import org.icd.surveyapi.exception.NotFoundSurveyException
import org.icd.surveyapi.support.utils.extract
import org.icd.surveyapi.surveyconsumer.application.dto.request.PostSurveyResponseRequest
import org.icd.surveycore.domain.event.CreateSurveyResponseEvent
import org.icd.surveycore.domain.survey.SurveyRepository
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
    fun postSurveyResponse(surveyId: Long, request: PostSurveyResponseRequest): String {
        val survey = surveyRepository.findByIdOrNull(surveyId) ?: throw NotFoundSurveyException()
        if (survey.responses.find { it.uuid == request.uuid } != null) {
            throw DuplicateSurveyResponseException()
        }

        request.validateRequiredItems(survey.getActiveItems())
        request.validateItemResponses(survey.getActiveItems())

        val surveyResponse = request.toEntity(survey)
        request.items.extract("items").map {
            surveyResponse.addItem(it.toEntity(surveyResponse, survey.getActiveItems()))
        }

        applicationEventPublisher.publishEvent(CreateSurveyResponseEvent(surveyResponse))
        return "OK"
    }
}