package study.crispin.surveyinfra.adaptor.dto

sealed class FormDto {
    data object LongInput : FormDto()
    data object ShortInput : FormDto()
    data class MultiSelect(val options: List<String>) : FormDto()
    data class SingleSelect(val options: List<String>) : FormDto()
}
