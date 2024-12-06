package org.icd.surveyapi.surveyproducer.application

import org.assertj.core.api.Assertions.assertThat
import org.icd.surveyapi.exception.DuplicateSurveyItemSequenceException
import org.icd.surveyapi.exception.InvalidSurveyItemCountException
import org.icd.surveyapi.exception.NotFoundSurveyException
import org.icd.surveyapi.support.BaseUnitTest
import org.icd.surveyapi.surveyproducer.fixture.createDuplicateSurveyItemSequencePostSurveyRequest
import org.icd.surveyapi.surveyproducer.fixture.createInvalidSurveyItemCountExceptionPostSurveyRequest
import org.icd.surveyapi.surveyproducer.fixture.createPostSurveyRequest
import org.icd.surveycore.domain.survey.Survey
import org.icd.surveycore.domain.survey.SurveyRepository
import org.icd.surveycore.domain.surveyItem.ItemType
import org.icd.surveycore.domain.surveyItem.SurveyItem
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull


class SurveyProducerServiceTest : BaseUnitTest() {

    @Autowired
    private lateinit var surveyRepository: SurveyRepository
    private val surveyProducerService: SurveyProducerService by lazy { createSurveyProducerService() }
    private fun createSurveyProducerService() = SurveyProducerService(surveyRepository)
    private fun insertSurvey(): Long {
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
        return surveyRepository.save(survey).id
    }

    @Test
    fun `설문조사 항목이 1~10개 사이가 아닌 경우 InvalidSurveyItemCountException 발생`() {
        val request = createInvalidSurveyItemCountExceptionPostSurveyRequest()
        assertThrows<InvalidSurveyItemCountException> { surveyProducerService.postSurvey(request) }
    }

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

    @Test
    fun `설문조사 조회 시 surveyId에 해당하는 설문조사가 없는 경우 NotFoundSurveyException 발생`() {
        assertThrows<NotFoundSurveyException> { surveyProducerService.getSurvey(1) }
    }

    @Test
    fun `설문조사가 정상적으로 조회된다`() {
        val surveyId = insertSurvey()
        val result = surveyProducerService.getSurvey(surveyId)
        assertThat(result.name).isEqualTo("테스트")
        assertThat(result.items).isNotEmpty
    }

}