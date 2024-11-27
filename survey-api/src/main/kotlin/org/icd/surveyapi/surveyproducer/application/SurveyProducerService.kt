package org.icd.surveyapi.surveyproducer.application

import jakarta.transaction.Transactional
import org.icd.surveyapi.exception.DuplicateSurveyItemSequenceException
import org.icd.surveyapi.support.utils.extract
import org.icd.surveyapi.surveyproducer.application.dto.request.PostSurveyItemRequest
import org.icd.surveyapi.surveyproducer.application.dto.request.PostSurveyRequest
import org.icd.surveyapi.surveyproducer.application.dto.response.PostSurveyResponse
import org.icd.surveycore.domain.survey.SurveyRepository
import org.springframework.stereotype.Service

@Service
class SurveyProducerService(
    private val surveyRepository: SurveyRepository
) {
    @Transactional
    fun postSurvey(request: PostSurveyRequest): PostSurveyResponse {
        val survey = request.toSurvey()
        val items = request.items.extract("items")
        validateSequence(items)

        items.map {
            val surveyItem = it.toSurveyItem(survey)
            surveyItem.addOptions(it.toSurveyItemOptions(surveyItem))
            survey.addItem(surveyItem)
        }

        val savedSurvey = surveyRepository.save(survey)

        return PostSurveyResponse(savedSurvey.id)
    }

    private fun validateSequence(items: List<PostSurveyItemRequest>) {
        val sequences = items.map { it.sequence }.toSet()
        if (sequences.size != items.size) {
            throw DuplicateSurveyItemSequenceException()
        }
    }

}