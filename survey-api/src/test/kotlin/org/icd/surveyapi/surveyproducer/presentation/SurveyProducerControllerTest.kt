package org.icd.surveyapi.surveyproducer.presentation

import org.icd.surveyapi.exception.DuplicateSurveyItemSequenceException
import org.icd.surveyapi.exception.MissingRequiredFieldException
import org.icd.surveyapi.support.BaseControllerTest
import org.icd.surveyapi.support.docs.ExceptionSnippet
import org.icd.surveyapi.surveyproducer.application.SurveyProducerService
import org.icd.surveyapi.surveyproducer.application.dto.response.PostSurveyResponse
import org.icd.surveyapi.surveyproducer.fixture.createPostSurveyRequest
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


class SurveyProducerControllerTest : BaseControllerTest() {
    private val surveyProducerService = mock<SurveyProducerService>()

    @Test
    fun postSurvey() {
        val request = createPostSurveyRequest()
        val response = PostSurveyResponse(1)

        given { surveyProducerService.postSurvey(any()) }.willReturn(response)

        mockMvc.perform(
            post("/v1/survey")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON),
        )
            .andExpect(status().isOk)
            .andDo(
                document(
                    "postSurvey",
                    requestFields(
                        fieldWithPath("name").description("설문조사의 이름"),
                        fieldWithPath("description").description("설문조사의 설명").optional(),
                        fieldWithPath("items[]").description("설문조사 항목"),
                        fieldWithPath("items[].sequence").description("항목의 순서"),
                        fieldWithPath("items[].name").description("항목의 이름"),
                        fieldWithPath("items[].description").description("항목의 설명").optional(),
                        fieldWithPath("items[].itemType").description("항목 유형"),
                        fieldWithPath("items[].options").description("선택지 옵션").optional()
                    ),
                    responseFields(
                        fieldWithPath("id").description("설문조사의 ID")
                    ),
                    ExceptionSnippet(listOf(MissingRequiredFieldException("필드명"), DuplicateSurveyItemSequenceException()))
                ),
            )
    }
}