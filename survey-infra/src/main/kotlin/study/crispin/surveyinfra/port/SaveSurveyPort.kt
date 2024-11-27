package study.crispin.surveyinfra.port

import study.crispin.surveyinfra.adaptor.dto.SurveyDto
import study.crispin.surveyinfra.adaptor.dto.SurveyItemDto

interface SaveSurveyPort {

    data class Request(
        val name: String,
        val description: String,
        val items: List<SurveyItemDto>,
    )

    fun save(request: Request): SurveyDto
}
