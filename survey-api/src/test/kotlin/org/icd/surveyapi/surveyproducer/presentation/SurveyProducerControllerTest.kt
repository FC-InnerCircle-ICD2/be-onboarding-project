package org.icd.surveyapi.surveyproducer.presentation

import org.icd.surveyapi.exception.DuplicateSurveyItemSequenceException
import org.icd.surveyapi.exception.MissingRequiredFieldException
import org.icd.surveyapi.exception.NotFoundSurveyException
import org.icd.surveyapi.support.BaseControllerTest
import org.icd.surveyapi.support.docs.ExceptionSnippet
import org.icd.surveyapi.surveyproducer.application.SurveyProducerService
import org.icd.surveyapi.surveyproducer.application.dto.response.PostSurveyResponse
import org.icd.surveyapi.surveyproducer.fixture.createGetSurveyResponse
import org.icd.surveyapi.surveyproducer.fixture.createPostSurveyRequest
import org.icd.surveycore.domain.surveyItem.ItemType
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.OffsetDateTime


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
                    fieldWithPath("id").description("설문조사의 ID").type(JsonFieldType.NUMBER),
                    fieldWithPath("name").description("설문조사의 이름").type(JsonFieldType.STRING),
                    fieldWithPath("description").description("설문조사의 설명").optional().type(JsonFieldType.STRING),
                    fieldWithPath("items[]").description("설문조사 항목").type(JsonFieldType.ARRAY),
                    fieldWithPath("items[].id").description("항목의 ID").type(JsonFieldType.NUMBER),
                    fieldWithPath("items[].isActive").description("항목의 활성 상태").type(JsonFieldType.BOOLEAN),
                    fieldWithPath("items[].sequence").description("항목의 순서").type(JsonFieldType.NUMBER),
                    fieldWithPath("items[].name").description("항목의 이름").type(JsonFieldType.STRING),
                    fieldWithPath("items[].description").description("항목의 설명").optional().type(JsonFieldType.STRING),
                    fieldWithPath("items[].itemType").description("항목 유형").type(generateEnumValues(ItemType::class.java)),
                    fieldWithPath("items[].options[]").description("선택지 옵션").optional().type(JsonFieldType.ARRAY),
                    fieldWithPath("items[].options[].id").description("선택지 옵션 ID").optional().type(JsonFieldType.NUMBER),
                    fieldWithPath("items[].options[].name").description("선택지 옵션 이름").optional().type(JsonFieldType.STRING),
                    fieldWithPath("items[].options[].isActive").description("옵션 활성화 여부").type(JsonFieldType.BOOLEAN),
                    fieldWithPath("items[].isRequired").description("항목 필수 응답 여부").type(JsonFieldType.BOOLEAN),
                    fieldWithPath("responses[]").description("응답 목록").optional().type(JsonFieldType.ARRAY),
                    fieldWithPath("responses[].id").description("응답의 ID").type(JsonFieldType.NUMBER),
                    fieldWithPath("responses[].createdAt").description("응답 제출 일시").type(OffsetDateTime::class.simpleName),
                    fieldWithPath("responses[].items[].sequence").description("항목 순서"),
                    fieldWithPath("responses[].items[].name").description("항목 이름"),
                    fieldWithPath("responses[].items[].description").description("항목 설명"),
                    fieldWithPath("responses[].items[].itemType").description("항목 유형"),
                    fieldWithPath("responses[].items[].shortResponse").description("단답형 응답").optional().type(JsonFieldType.STRING),
                    fieldWithPath("responses[].items[].longResponse").description("장문형 응답").optional().type(JsonFieldType.STRING),
                    fieldWithPath("responses[].items[].singleChoiceResponse").description("단일 선택형 응답").optional(),
                    fieldWithPath("responses[].items[].singleChoiceResponse.itemOptionId").description("옵션 ID").optional().type(JsonFieldType.NUMBER),
                    fieldWithPath("responses[].items[].singleChoiceResponse.itemOptionName").description("옵션 이름").optional().type(JsonFieldType.STRING),
                    fieldWithPath("responses[].items[].multipleChoiceResponse").description("다중 선택형 응답").optional(),
                    fieldWithPath("responses[].items[].multipleChoiceResponse[].itemOptionId").description("옵션 ID").optional().type(JsonFieldType.NUMBER),
                    fieldWithPath("responses[].items[].multipleChoiceResponse[].itemOptionName").description("옵션 이름").optional().type(JsonFieldType.STRING),

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