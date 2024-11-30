package org.icd.surveyapi.surveyconsumer.fixture

import org.icd.surveyapi.surveyconsumer.application.dto.request.PostSurveyResponseItemRequest
import org.icd.surveyapi.surveyconsumer.application.dto.request.PostSurveyResponseRequest

fun createPostSurveyResponseRequest() = PostSurveyResponseRequest(
    uuid = "uuid",
    items = listOf(
        PostSurveyResponseItemRequest(
            itemId = 1,
            answer = "단답형, 장문형"
        ),
        PostSurveyResponseItemRequest(
            itemId = 2,
            itemOptionId = 1,
        ),
        PostSurveyResponseItemRequest(
            itemId = 3,
            itemOptionIds = listOf(1,2,3)
        )
    )
)