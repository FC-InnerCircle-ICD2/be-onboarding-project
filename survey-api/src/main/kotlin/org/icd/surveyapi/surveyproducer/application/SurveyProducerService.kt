package org.icd.surveyapi.surveyproducer.application

import org.icd.surveyapi.exception.*
import org.icd.surveyapi.support.utils.extract
import org.icd.surveyapi.surveyproducer.application.dto.request.CreateSurveyItemRequest
import org.icd.surveyapi.surveyproducer.application.dto.request.PatchSurveyRequest
import org.icd.surveyapi.surveyproducer.application.dto.request.PostSurveyRequest
import org.icd.surveyapi.surveyproducer.application.dto.request.UpdateSurveyItemRequest
import org.icd.surveyapi.surveyproducer.application.dto.response.GetSurveyResponse
import org.icd.surveyapi.surveyproducer.application.dto.response.PostSurveyResponse
import org.icd.surveycore.domain.survey.Survey
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
        validateSequence(items.map { it.sequence.extract("sequence") })

        items.map {
            val surveyItem = it.toSurveyItem(survey)
            surveyItem.addOptions(it.toSurveyItemOptions(surveyItem))
            survey.addItem(surveyItem)
        }

        val savedSurvey = surveyRepository.save(survey)

        return PostSurveyResponse(savedSurvey.id)
    }

    private fun validateSequence(sequences: List<Int>) {
        if (sequences.size !in 1..10) {
            throw InvalidSurveyItemCountException()
        }
        val sequenceSet = sequences.map { it }.toSet()
        if (sequenceSet.size != sequences.size) {
            throw DuplicateSurveyItemSequenceException()
        }
    }

    fun getSurvey(surveyId: Long): GetSurveyResponse {
        val survey = surveyRepository.findByIdOrNull(surveyId) ?: throw NotFoundSurveyException()
        return GetSurveyResponse(survey)
    }

    @Transactional
    fun patchSurvey(
        surveyId: Long,
        request: PatchSurveyRequest
    ): String {
        val survey = surveyRepository.findByIdOrNull(surveyId) ?: throw NotFoundSurveyException()
        survey.update(
            request.name.extract("name"),
            request.description.extract("description")
        )

        deleteItems(survey, request.deleteItems)
        updateItems(survey, request.updateItems)
        createItems(survey, request.createItems)

        validateSequence(survey.getActiveItems().map { it.sequence })

        return "OK"
    }

    private fun deleteItems(
        survey: Survey,
        deleteItems: List<Long>
    ) {
        val items = survey.items.filter { it.id in deleteItems }
        items.forEach { it.delete() }
    }

    fun updateItems(
        survey: Survey,
        updateItems: List<UpdateSurveyItemRequest>?
    ) {
        updateItems?.forEach { request ->
            val itemsById = survey.items.associateBy { it.id }
            val item = itemsById[request.id] ?: throw NotFoundSurveyItemException()

            item.update(
                sequence = request.sequence.extract("sequence"),
                name = request.name.extract("name"),
                description = request.description,
                itemType = request.itemType.extract("itemType"),
                isRequired = request.isRequired.extract("isRequired"),
            )

            val optionsById = item.options.associateBy { it.id }
            request.deleteOptions.forEach { optionId ->
                optionsById[optionId]?.delete()
            }
            request.updateOptions.forEach { option ->
                val itemOption = optionsById[option.id] ?: throw NotFoundSurveyItemOptionException()
                itemOption.updateName(option.name.extract("name"))
            }
            request.toSurveyItemOptions(item)?.let { item.options.addAll(it) }
        }
    }

    private fun createItems(
        survey: Survey,
        createItems: List<CreateSurveyItemRequest>?,
    ) {
        createItems?.map {
            val surveyItem = it.toSurveyItem(survey)
            surveyItem.addOptions(it.toSurveyItemOptions(surveyItem))
            survey.addItem(surveyItem)
        }
    }

}