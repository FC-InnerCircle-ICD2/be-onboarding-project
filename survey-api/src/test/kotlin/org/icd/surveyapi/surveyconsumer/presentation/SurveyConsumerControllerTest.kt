package org.icd.surveyapi.surveyconsumer.presentation

import org.icd.surveyapi.exception.*
import org.icd.surveyapi.support.BaseControllerTest
import org.icd.surveyapi.support.docs.ExceptionSnippet
import org.icd.surveyapi.surveyconsumer.application.SurveyConsumerService
import org.icd.surveyapi.surveyconsumer.fixture.createPostSurveyResponseRequest
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


class SurveyConsumerControllerTest : BaseControllerTest() {
    @MockitoBean
    private lateinit var surveyConsumerService: SurveyConsumerService

    @Test
    fun postSurveyResponse() {
        val request = createPostSurveyResponseRequest()

        given { surveyConsumerService.postSurveyResponse(any(), any()) }.willReturn("OK")

        mockMvc.perform(
            post("/v1/survey/{surveyId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON),
        ).andDo(
            document(
                "postSurveyResponse",
                pathParameters(
                    parameterWithName("surveyId").description("설문조사 ID")
                ),
                requestFields(
                    fieldWithPath("uuid").description("설문조사 응답 페이지별 unique ID"),
                    fieldWithPath("items[]").description("설문조사 응답 항목"),
                    fieldWithPath("items[].itemId").description("설문조사 응답 항목 ID"),
                    fieldWithPath("items[].answer").description("단답형, 장문형 인 경우 필수").optional(),
                    fieldWithPath("items[].itemOptionId").description("단일 선택 리스트인 경우 필수").optional(),
                    fieldWithPath("items[].itemOptionIds").description("다중 선택 리스트인 경우 필수").optional(),
                ),
                ExceptionSnippet(
                    listOf(
                        MissingRequiredFieldException("필드명"),
                        NotFoundSurveyException(),
                        DuplicateSurveyResponseException(),
                        MissingRequiredSurveyResponseItemException(),
                        NotFoundSurveyItemException(),
                        InvalidSurveyResponseOptionException()
                    )
                )
            ),
        ).andExpect(status().isOk)
    }
}