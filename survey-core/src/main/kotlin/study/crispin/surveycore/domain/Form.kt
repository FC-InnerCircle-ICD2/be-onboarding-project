package study.crispin.surveycore.domain

sealed class Form {
    data object LongInput : Form()
    data object ShortInput : Form()
    data class MultiSelect(val options: List<String>) : Form()
    data class SingleSelect(val options: List<String>) : Form()
}
