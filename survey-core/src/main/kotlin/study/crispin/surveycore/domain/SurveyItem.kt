package study.crispin.surveycore.domain

data class SurveyItem(
    val name: String,
    val description: String,
    val form: Form,
    val required: Boolean,
)
