package study.crispin.surveyinfra.port

import java.util.UUID
import study.crispin.surveyinfra.adaptor.dto.SurveyItemDto

interface UpdateSurveyPort {

    data class Request(
        val id: UUID,
        val name: String,
        val description: String,
        val items: List<SurveyItemDto>
    )

    fun update(request: Request)
}
