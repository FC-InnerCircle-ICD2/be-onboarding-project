package org.icd.surveyapi.surveyproducer.fixture

import org.icd.surveyapi.surveyproducer.application.dto.request.*
import org.icd.surveyapi.surveyproducer.application.dto.response.*
import org.icd.surveycore.domain.surveyItem.ItemType
import java.time.OffsetDateTime

fun createInvalidSurveyItemCountExceptionPostSurveyRequest(): PostSurveyRequest {
    return PostSurveyRequest(
        name = "테스트 설문조사",
        description = "설문조사 작성 테스트",
        items = (1..11).map {
            PostSurveyItemRequest(
                sequence = it,
                name = "$it",
                itemType = ItemType.SHORT_ANSWER,
                isRequired = true
            )
        }
    )
}

fun createDuplicateSurveyItemSequencePostSurveyRequest(): PostSurveyRequest {
    return PostSurveyRequest(
        name = "테스트 설문조사",
        description = "설문조사 작성 테스트",
        items = listOf(
            PostSurveyItemRequest(
                sequence = 1,
                name = "1번 항목",
                itemType = ItemType.SHORT_ANSWER,
                isRequired = false
            ),
            PostSurveyItemRequest(
                sequence = 1,
                name = "2번 항목",
                itemType = ItemType.MULTIPLE_CHOICE,
                options = listOf("가", "나", "다", "라"),
                isRequired = true
            ),
        )
    )
}

fun createPostSurveyRequest(): PostSurveyRequest {
    return PostSurveyRequest(
        name = "테스트 설문조사",
        description = "설문조사 작성 테스트",
        items = listOf(
            PostSurveyItemRequest(
                sequence = 1,
                name = "1번 항목",
                itemType = ItemType.SHORT_ANSWER,
                isRequired = true
            ),
            PostSurveyItemRequest(
                sequence = 2,
                name = "2번 항목",
                itemType = ItemType.MULTIPLE_CHOICE,
                options = listOf("가", "나", "다", "라"),
                isRequired = false
            ),
        )
    )
}

fun createGetSurveyResponse() = GetSurveyResponse(
    id = 1,
    name = "테스트",
    description = "설명",
    items = listOf(
        GetSurveyItemResponse(
            id = 1,
            isActive = true,
            sequence = 1,
            name = "항목",
            description = "설명",
            itemType = ItemType.MULTIPLE_CHOICE,
            options = listOf(
                GetSurveyItemOptionResponse(1, "가", true),
                GetSurveyItemOptionResponse(2, "나", true),
                GetSurveyItemOptionResponse(3, "다", false)
            ),
            isRequired = true
        )
    ),
)

fun createGetSurveyResponseResponse(): List<GetSurveyResponseResponse> =
    listOf(
        GetSurveyResponseResponse(
            id = 1,
            createdAt = OffsetDateTime.now(),
            items = listOf(
                GetSurveyResponseItemResponse(
                    sequence = 1,
                    name = "응답",
                    description = "응답",
                    itemType = ItemType.MULTIPLE_CHOICE,
                    shortResponse = null,
                    longResponse = null,
                    singleChoiceResponse = null,
                    multipleChoiceResponse = listOf(
                        GetSurveyResponseItemOptionResponse(
                            itemOptionId = 1,
                            itemOptionName = "가"
                        )
                    )
                )
            )
        )
    )

fun createPatchSurveyRequest(): PatchSurveyRequest {
    return PatchSurveyRequest(
        name = "테스트 설문조사 수정",
        description = "설문조사 수정 테스트",
        deleteItems = listOf(1),
        updateItems = listOf(
            UpdateSurveyItemRequest(
                id = 2,
                sequence = 2,
                name = "2번 항목 수정",
                description = null,
                itemType = ItemType.MULTIPLE_CHOICE,
                deleteOptions = listOf(1),
                updateOptions = listOf(
                    UpdateSurveyItemOptionRequest(
                        id = 2,
                        name = "옵션명 변경"
                    )
                ),
                createOptions = listOf("새로운 옵션"),
                isRequired = true,
            )
        ),
        createItems = listOf(
            CreateSurveyItemRequest(
                sequence = 3,
                name = "새로운 항목",
                description = "추가",
                itemType = ItemType.SINGLE_CHOICE,
                options = listOf("옵션1", "옵션2"),
                isRequired = false
            )
        )
    )
}