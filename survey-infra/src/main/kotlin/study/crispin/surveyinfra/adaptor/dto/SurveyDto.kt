package study.crispin.surveyinfra.adaptor.dto

import java.util.UUID
import study.crispin.surveyinfra.adaptor.dto.response.SurveyResponseItemDto

data class SurveyDto(
    val id: UUID,
    val name: String,
    val description: String,
    val items: List<SurveyResponseItemDto> = emptyList(),
) {
    fun update(items: List<SurveyResponseItemDto>): SurveyDto {
        return SurveyDto(
            id = this.id,
            name = this.name,
            description = this.description,
            items = items,
        )
    }
}
