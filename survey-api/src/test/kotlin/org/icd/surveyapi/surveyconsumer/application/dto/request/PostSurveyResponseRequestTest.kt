package org.icd.surveyapi.surveyconsumer.application.dto.request

import org.icd.surveyapi.exception.InvalidSurveyResponseOptionException
import org.icd.surveyapi.exception.MissingRequiredSurveyResponseItemException
import org.icd.surveycore.domain.survey.Survey
import org.icd.surveycore.domain.surveyItem.ItemType
import org.icd.surveycore.domain.surveyItem.SurveyItem
import org.icd.surveycore.domain.surveyItem.SurveyItemOption
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.NullAndEmptySource
import org.junit.jupiter.params.provider.NullSource

class PostSurveyResponseRequestTest {
    private fun createSurveyWithItem(
        itemType: ItemType,
        isRequired: Boolean = true
    ): SurveyItem {
        val survey = Survey(id = 1, name = "테스트", description = "설명")
        val surveyItem = SurveyItem(
            id = 1,
            survey = survey,
            sequence = 1,
            name = "항목",
            description = "설명",
            itemType = itemType,
            isRequired = isRequired
        )
        survey.addItem(surveyItem)
        return surveyItem
    }

    private fun createSurveyResponseRequest(
        surveyItem: SurveyItem,
        itemOptionId: Long? = null,
        itemOptionIds: List<Long>? = null,
        answer: String? = null
    ): PostSurveyResponseRequest {
        return PostSurveyResponseRequest(
            uuid = "uuid",
            items = listOf(
                PostSurveyResponseItemRequest(
                    surveyItem.id,
                    answer = answer,
                    itemOptionId = itemOptionId,
                    itemOptionIds = itemOptionIds
                )
            )
        )
    }

    @Test
    fun `필수 항목 응답하지 않았을 때 MissingRequiredSurveyResponseItemException 발생`() {
        val surveyItems = listOf(
            SurveyItem(
                id = 1,
                survey = Survey.of(name = "설문", description = "설명"),
                sequence = 1,
                name = "항목",
                itemType = ItemType.SHORT_ANSWER,
                isRequired = false
            ),
            SurveyItem(
                id = 2,
                survey = Survey.of(name = "설문", description = "설명"),
                sequence = 2,
                name = "항목",
                itemType = ItemType.SHORT_ANSWER,
                isRequired = true
            )
        )
        val request = PostSurveyResponseRequest(
            "uuid",
            items = listOf(
                PostSurveyResponseItemRequest(
                    itemId = 1,
                    answer = "응답1"
                )
            )
        )

        assertThrows<MissingRequiredSurveyResponseItemException> {
            request.validateRequiredItems(surveyItems)
        }
    }

    @ParameterizedTest
    @NullAndEmptySource
    fun `설문조사 응답 시 itemType SHORT_ANSWER 일때 answer가 null 이면 MissingRequiredSurveyResponseItemException 발생`(answer: String?) {
        val surveyItem = createSurveyWithItem(ItemType.SHORT_ANSWER)
        val request = createSurveyResponseRequest(surveyItem, answer = answer)

        assertThrows<MissingRequiredSurveyResponseItemException> {
            request.validateItemResponses(listOf(surveyItem))
        }
    }

    @ParameterizedTest
    @NullSource
    fun `설문조사 응답 시 itemType SINGLE_CHOICE 일때 itemOptionId가 null 이면 MissingRequiredSurveyResponseItemException 발생`(
        itemOptionId: Long?
    ) {
        val surveyItem = createSurveyWithItem(ItemType.SINGLE_CHOICE)
        val request = createSurveyResponseRequest(surveyItem, itemOptionId = itemOptionId)

        assertThrows<MissingRequiredSurveyResponseItemException> {
            request.validateItemResponses(listOf(surveyItem))
        }
    }

    @Test
    fun `설문조사 응답 시 itemType SINGLE_CHOICE 일때 itemOptionId가 유효하지 않으면 InvalidSurveyResponseOptionException 발생`() {
        val surveyItem = createSurveyWithItem(ItemType.SINGLE_CHOICE)
        surveyItem.addOptions(listOf(SurveyItemOption.of(surveyItem, "옵션")))
        val request = createSurveyResponseRequest(surveyItem, itemOptionId = 100)

        assertThrows<InvalidSurveyResponseOptionException> {
            request.validateItemResponses(listOf(surveyItem))
        }
    }

    @ParameterizedTest
    @NullSource
    fun `설문조사 응답 시 itemType MULTIPLE_CHOICE 일때 itemOptionIds가 null 이면 MissingRequiredSurveyResponseItemException 발생`(
        itemOptionIds: List<Long>?
    ) {
        val surveyItem = createSurveyWithItem(ItemType.MULTIPLE_CHOICE)
        surveyItem.addOptions(listOf(SurveyItemOption.of(surveyItem, "옵션")))
        val request = createSurveyResponseRequest(surveyItem, itemOptionIds = itemOptionIds)

        assertThrows<MissingRequiredSurveyResponseItemException> {
            request.validateItemResponses(listOf(surveyItem))
        }
    }

    @Test
    fun `설문조사 응답 시 itemType MULTIPLE_CHOICE 일때 itemOptionIds가 유효하지 않으면 InvalidSurveyResponseOptionException 발생`() {
        val surveyItem = createSurveyWithItem(ItemType.MULTIPLE_CHOICE)
        surveyItem.addOptions(listOf(SurveyItemOption.of(surveyItem, "옵션")))
        val request = createSurveyResponseRequest(surveyItem, itemOptionIds = listOf(100))

        assertThrows<InvalidSurveyResponseOptionException> {
            request.validateItemResponses(listOf(surveyItem))
        }
    }
}