package study.crispin.surveyinfra.port

import study.crispin.surveyinfra.adaptor.dto.SurveyDto
import study.crispin.surveyinfra.adaptor.dto.request.SurveyRequestItemDto

interface SaveSurveyPort {

    data class Request(
        val name: String,
        val description: String,
        val items: List<SurveyRequestItemDto>,
    )

    fun save(request: Request): SurveyDto
}
