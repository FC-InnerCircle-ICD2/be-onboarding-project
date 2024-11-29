package org.icd.surveyapi.surveyproducer.fixture

import org.icd.surveyapi.surveyproducer.application.dto.request.PostSurveyItemRequest
import org.icd.surveyapi.surveyproducer.application.dto.request.PostSurveyRequest
import org.icd.surveycore.domain.surveyItem.ItemType

fun createInvalidSurveyItemCountExceptionPostSurveyRequest(): PostSurveyRequest {
    return PostSurveyRequest(
        name = "테스트 설문조사",
        description = "설문조사 작성 테스트",
        items = (1..11).map { PostSurveyItemRequest(sequence = it, name = "$it", itemType = ItemType.SHORT_ANSWER) }
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
            ),
            PostSurveyItemRequest(
                sequence = 1,
                name = "2번 항목",
                itemType = ItemType.MULTIPLE_CHOICE,
                options = listOf("가", "나", "다", "라")
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
            ),
            PostSurveyItemRequest(
                sequence = 2,
                name = "2번 항목",
                itemType = ItemType.MULTIPLE_CHOICE,
                options = listOf("가", "나", "다", "라")
            ),
        )
    )
}