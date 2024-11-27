package study.crispin.surveycore.service

import study.crispin.surveycore.domain.Survey
import study.crispin.surveycore.domain.SurveyItem

interface CreateSurveyUseCase {

    data class Request(
        val name: String,
        val description: String,
        val items: List<SurveyItem>
    )

    fun createSurvey(request: Request): Survey
}
