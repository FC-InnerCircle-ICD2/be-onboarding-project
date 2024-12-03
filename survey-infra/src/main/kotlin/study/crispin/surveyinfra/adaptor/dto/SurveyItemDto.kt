package study.crispin.surveyinfra.adaptor.dto

data class SurveyItemDto(
    val name: String,
    val description: String,
    val form: FormDto,
    val required: Boolean,
)
