package org.icd.surveyapi.surveyproducer.application.dto.request

import org.assertj.core.api.Assertions.assertThat
import org.icd.surveyapi.support.utils.extract
import org.icd.surveyapi.surveyproducer.fixture.createPostSurveyRequest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class PostSurveyRequestTest {
    @Test
    fun `설문조사 도메인이 정상적으로 생성된다`() {
        val request = createPostSurveyRequest()
        val survey = request.toSurvey()
        request.items.extract("items").map {
            val surveyItem = it.toSurveyItem(survey)
            surveyItem.addOptions(it.toSurveyItemOptions(surveyItem))
            survey.addItem(surveyItem)
        }

        assertAll(
            { assertThat(survey.name).isEqualTo(request.name) },
            {
                val surveyItem = survey.items.find { it.sequence == 2 }
                assertThat(surveyItem).isNotNull
                assertThat(surveyItem?.options).isNotNull
            }
        )
    }
}