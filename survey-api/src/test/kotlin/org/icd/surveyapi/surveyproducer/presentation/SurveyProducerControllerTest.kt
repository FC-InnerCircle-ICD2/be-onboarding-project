package org.icd.surveyapi.surveyproducer.presentation

import org.assertj.core.api.Assertions.assertThat
import org.icd.surveyapi.exception.DuplicateSurveyItemSequenceException
import org.icd.surveyapi.exception.MissingRequiredFieldException
import org.icd.surveyapi.exception.NotFoundSurveyException
import org.icd.surveyapi.support.BaseControllerTest
import org.icd.surveyapi.support.docs.ExceptionSnippet
import org.icd.surveyapi.surveyproducer.application.SurveyProducerService
import org.icd.surveyapi.surveyproducer.application.dto.response.PostSurveyResponse
import org.icd.surveyapi.surveyproducer.fixture.createGetSurveyResponse
import org.icd.surveyapi.surveyproducer.fixture.createPostSurveyRequest
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


class SurveyProducerControllerTest : BaseControllerTest() {
    @MockitoBean
    private lateinit var surveyProducerService: SurveyProducerService

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
        ).andDo(
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
                    fieldWithPath("items[].options").description("선택지 옵션").optional(),
                    fieldWithPath("items[].isRequired").description("항목 필수 응답 여부"),
                ),
                responseFields(
                    fieldWithPath("id").description("설문조사의 ID")
                ),
                ExceptionSnippet(
                    listOf(
                        MissingRequiredFieldException("필드명"),
                        DuplicateSurveyItemSequenceException()
                    )
                )
            ),
        ).andExpect(status().isOk)
    }

    @Test
    fun `mocking이 정상적으로 동작한다`() {
        val response = createGetSurveyResponse()
        given { surveyProducerService.getSurvey(any()) }.willReturn(response)

        val result = surveyProducerService.getSurvey(1L)
        assertThat(result).isEqualTo(response)
    }

    @Test
    fun getSurvey() {
        val response = createGetSurveyResponse()
        given { surveyProducerService.getSurvey(any()) }.willReturn(response)

        mockMvc.perform(
            get("/v1/survey/{surveyId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(
            document(
                "getSurvey",
                pathParameters(
                    parameterWithName("surveyId").description("설문조사 ID")
                ),
                responseFields(
                    fieldWithPath("id").description("설문조사의 ID"),
                    fieldWithPath("name").description("설문조사의 이름"),
                    fieldWithPath("description").description("설문조사의 설명").optional(),
                    fieldWithPath("items[]").description("설문조사 항목"),
                    fieldWithPath("items[].id").description("항목의 ID"),
                    fieldWithPath("items[].isActive").description("항목의 활성 상태"),
                    fieldWithPath("items[].sequence").description("항목의 순서"),
                    fieldWithPath("items[].name").description("항목의 이름"),
                    fieldWithPath("items[].description").description("항목의 설명").optional(),
                    fieldWithPath("items[].itemType").description("항목 유형"),
                    fieldWithPath("items[].options[]").description("선택지 옵션").optional(),
                    fieldWithPath("items[].isRequired").description("항목 필수 응답 여부"),
                ),
                ExceptionSnippet(
                    listOf(
                        NotFoundSurveyException()
                    )
                )
            ),
        ).andExpect(status().isOk)
    }
}