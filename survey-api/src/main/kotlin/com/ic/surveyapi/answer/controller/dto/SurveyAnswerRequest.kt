package com.ic.surveyapi.answer.controller.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import survey.type.ItemType

// TODO - 클래스 네이밍을 다시 고려 필요, 벨리데이션 처리가 필요
data class SurveyAnswerRequest(
    // TODO - Validator 안먹히는 원인 확인 필요
    @field:Size(min = 1, max = 10, message = "유효한 항목 갯수는 1개에서 10개 사이 입니다.")
    val surveyItems: List<SurveyItem>,
) {
    data class SurveyItem(
        // TODO - name 중복 되면 안됨 !!
        @field:NotBlank(message = "Survey Answer Name 은 값이 유효 하여야 합니다.")
        val name: String,
        val type: ItemType,
        val answer: String? = null,
        // TODO - Set 으로 변경하기 !
        val selectedOptions: Set<String> = emptySet(),
    )
}
