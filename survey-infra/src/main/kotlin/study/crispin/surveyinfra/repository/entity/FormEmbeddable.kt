package study.crispin.surveyinfra.repository.entity

data class FormEmbeddable(
    val type: FormType,
    val options: List<String> = listOf(),
)
