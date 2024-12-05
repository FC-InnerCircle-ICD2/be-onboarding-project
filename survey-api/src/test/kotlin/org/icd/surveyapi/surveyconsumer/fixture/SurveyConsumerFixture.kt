package org.icd.surveyapi.surveyconsumer.fixture

import org.icd.surveyapi.surveyconsumer.application.dto.request.PostSurveyResponseItemRequest
import org.icd.surveyapi.surveyconsumer.application.dto.request.PostSurveyResponseRequest

fun createPostSurveyResponseRequest() = PostSurveyResponseRequest(
    uuid = "uuid",
    items = listOf(
        PostSurveyResponseItemRequest(
            itemId = 1,
            shortResponse = "단답형"
        ),
        PostSurveyResponseItemRequest(
            itemId = 2,
            longResponse = "장문형"
        ),
        PostSurveyResponseItemRequest(
            itemId = 3,
            singleChoiceOptionId = 1,
        ),
        PostSurveyResponseItemRequest(
            itemId = 4,
            multipleChoiceOptionIds = listOf(1, 2, 3)
        )
    )
)