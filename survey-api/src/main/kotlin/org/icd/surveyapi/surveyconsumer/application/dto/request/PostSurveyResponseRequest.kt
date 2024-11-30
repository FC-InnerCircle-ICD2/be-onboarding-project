package org.icd.surveyapi.surveyconsumer.application.dto.request

import org.icd.surveyapi.support.utils.extract
import org.icd.surveycore.domain.survey.Survey
import org.icd.surveycore.domain.surveyresponse.SurveyResponse
import org.icd.surveycore.domain.surveyresponse.SurveyResponseItem

data class PostSurveyResponseRequest(
    val uuid: String?,
    val items: List<PostSurveyResponseItemRequest>?
) {
    fun toEntity(survey: Survey): SurveyResponse {
        return SurveyResponse.of(
            survey = survey,
            uuid = uuid.extract("uuid"),
        )
    }
}

data class PostSurveyResponseItemRequest(
    val itemId: Long,
    val answer: String?,
    val itemOptionId: Long?,
    val itemOptionIds: List<Long>?
) {
    fun toEntity(surveyResponse: SurveyResponse): SurveyResponseItem {
        return SurveyResponseItem.of(
            surveyResponse = surveyResponse,
            answer = answer,
            itemOptionId = itemOptionId,
            itemOptionIds = itemOptionIds
        )
    }
}
