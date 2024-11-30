package org.icd.surveyapi.surveyconsumer.application.event

import org.icd.surveyapi.exception.DuplicateSurveyResponseException
import org.icd.surveycore.domain.event.CreateSurveyResponseEvent
import org.icd.surveycore.domain.event.ExistsSurveyResponseEvent
import org.icd.surveycore.domain.surveyresponse.SurveyResponseRepository
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class SurveyResponseEventListener(
    private val surveyResponseRepository: SurveyResponseRepository
) {
    @EventListener
    fun onCreateSurveyResponseEvent(event: CreateSurveyResponseEvent) {
        surveyResponseRepository.save(event.surveyResponse)
    }

    @EventListener
    fun onExistsSurveyResponseEvent(event: ExistsSurveyResponseEvent) {
        if (surveyResponseRepository.existsByUuid(event.uuid)) {
            throw DuplicateSurveyResponseException()
        }
    }
}