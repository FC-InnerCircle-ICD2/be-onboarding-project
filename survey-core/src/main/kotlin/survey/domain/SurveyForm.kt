package survey.domain

import survey.type.ItemType

data class SurveyForm(
    val title: String,
    val description: String,
    val surveyItems: List<SurveyItem>,
) {
    data class SurveyItem(
        val name: String,
        val type: ItemType,
        val description: String,
        val isRequired: Boolean,
        val options: List<Option>,
    )

    data class Option(
        val name: String,
    )
}
