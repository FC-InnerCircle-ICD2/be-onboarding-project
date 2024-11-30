package org.icd.surveyapi.surveyproducer.application

import org.icd.surveyapi.exception.DuplicateSurveyItemSequenceException
import org.icd.surveyapi.exception.InvalidSurveyItemCountException
import org.icd.surveyapi.exception.NotFoundSurveyException
import org.icd.surveyapi.support.utils.extract
import org.icd.surveyapi.surveyproducer.application.dto.request.PostSurveyItemRequest
import org.icd.surveyapi.surveyproducer.application.dto.request.PostSurveyRequest
import org.icd.surveyapi.surveyproducer.application.dto.response.GetSurveyResponse
import org.icd.surveyapi.surveyproducer.application.dto.response.PostSurveyResponse
import org.icd.surveycore.domain.survey.SurveyRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
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
        if (items.size !in 1..10) {
            throw InvalidSurveyItemCountException()
        }
        val sequences = items.map { it.sequence }.toSet()
        if (sequences.size != items.size) {
            throw DuplicateSurveyItemSequenceException()
        }
    }

    fun getSurvey(surveyId: Long): GetSurveyResponse {
        val survey = surveyRepository.findByIdOrNull(surveyId) ?: throw NotFoundSurveyException()
        return GetSurveyResponse(survey)
    }

}