package org.icd.surveyapi.surveyproducer.application

import org.assertj.core.api.Assertions.assertThat
import org.icd.surveyapi.exception.DuplicateSurveyItemSequenceException
import org.icd.surveyapi.support.BaseUnitTest
import org.icd.surveyapi.surveyproducer.fixture.createDuplicateSurveyItemSequencePostSurveyRequest
import org.icd.surveyapi.surveyproducer.fixture.createPostSurveyRequest
import org.icd.surveycore.domain.survey.SurveyRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull


class SurveyProducerServiceTest : BaseUnitTest() {

    @Autowired
    private lateinit var surveyRepository: SurveyRepository
    private val surveyProducerService: SurveyProducerService by lazy { createSurveyProducerService() }
    private fun createSurveyProducerService() = SurveyProducerService(surveyRepository)

    @Test
    fun `설문조사 항목 sequence가 중복된 경우 DuplicateSurveyItemSequenceException 발생`() {
        val request = createDuplicateSurveyItemSequencePostSurveyRequest()
        assertThrows<DuplicateSurveyItemSequenceException> { surveyProducerService.postSurvey(request) }
    }

    @Test
    fun `설문조사가 정상적으로 등록된다`() {
        val request = createPostSurveyRequest()
        val result = surveyProducerService.postSurvey(request)

        val survey = surveyRepository.findByIdOrNull(result.id)
        assertThat(survey).isNotNull
    }
}