package org.icd.surveyapi.surveyconsumer.application

import org.assertj.core.api.Assertions.assertThat
import org.icd.surveyapi.exception.DuplicateSurveyResponseException
import org.icd.surveyapi.exception.NotFoundSurveyException
import org.icd.surveyapi.support.BaseUnitTest
import org.icd.surveyapi.surveyconsumer.application.dto.request.PostSurveyResponseItemRequest
import org.icd.surveyapi.surveyconsumer.application.dto.request.PostSurveyResponseRequest
import org.icd.surveycore.domain.survey.Survey
import org.icd.surveycore.domain.survey.SurveyRepository
import org.icd.surveycore.domain.surveyItem.ItemType
import org.icd.surveycore.domain.surveyItem.SurveyItem
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull

class SurveyConsumerServiceTest : BaseUnitTest() {

    @Autowired
    private lateinit var surveyRepository: SurveyRepository

    @Autowired
    private lateinit var applicationEventPublisher: ApplicationEventPublisher
    private val surveyConsumerService: SurveyConsumerService by lazy { createSurveyConsumerService() }
    private fun createSurveyConsumerService() = SurveyConsumerService(surveyRepository, applicationEventPublisher)
    private fun insertSurvey(): Survey {
        val survey = Survey.of(name = "테스트", description = "설명")
        val surveyItem = SurveyItem.of(
            survey = survey,
            sequence = 1,
            name = "항목",
            description = "설명",
            itemType = ItemType.SHORT_ANSWER,
            isRequired = false
        )
        survey.addItem(surveyItem)
        return surveyRepository.save(survey)
    }

    @AfterEach
    fun deleteAll() {
        surveyRepository.deleteAll()
    }

    @Test
    fun `설문조사 응답 시 해당 설문조사가 없는 경우 NotFoundSurveyException 발생`() {
        val request = PostSurveyResponseRequest("uuid", listOf())
        assertThrows<NotFoundSurveyException> {
            surveyConsumerService.postSurveyResponse(1, request)
        }
    }

    @Test
    fun `설문조사 응답 시 uuid가 중복인 경우 DuplicateSurveyResponseException 발생`() {
        val survey = insertSurvey()
        val firstRequest = PostSurveyResponseRequest(
            uuid = "duplicate-uuid",
            items = listOf(PostSurveyResponseItemRequest(itemId = 1, shortResponse = "응답1"))
        )
        surveyConsumerService.postSurveyResponse(survey.id, firstRequest)
        val secondRequest = PostSurveyResponseRequest(
            uuid = "duplicate-uuid",
            items = listOf(PostSurveyResponseItemRequest(itemId = 1, shortResponse = "응답2"))
        )

        assertThrows<DuplicateSurveyResponseException> {
            surveyConsumerService.postSurveyResponse(survey.id, secondRequest)
        }
    }

    @Test
    fun `설문조사 응답이 정상적으로 등록된다`() {
        val survey = insertSurvey()
        val request = PostSurveyResponseRequest(
            uuid = "uuid",
            items = listOf(
                PostSurveyResponseItemRequest(
                    itemId = survey.items.first().id, shortResponse = "응답1"
                )
            )
        )
        surveyConsumerService.postSurveyResponse(survey.id, request)
        val result = surveyRepository.findByIdOrNull(survey.id)
        assertThat(result?.responses?.count()).isGreaterThan(0)
    }

}