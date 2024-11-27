package study.crispin.surveyinfra.adaptor.dto

import java.util.UUID

data class SurveyDto(
    val id: UUID,
    val name: String,
    val description: String,
    val items: List<SurveyItemDto> = emptyList(),
) {
    fun update(items: List<SurveyItemDto>): SurveyDto {
        return SurveyDto(
            id = this.id,
            name = this.name,
            description = this.description,
            items = items,
        )
    }
}
