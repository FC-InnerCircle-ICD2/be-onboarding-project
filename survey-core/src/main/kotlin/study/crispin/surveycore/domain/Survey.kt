package study.crispin.surveycore.domain

import java.util.UUID

data class Survey(
    val id: UUID? = null,
    val name: String,
    val description: String,
    val items: List<SurveyItem>
)
