package com.ic.surveyapi.util

import com.ic.surveyapi.answer.controller.dto.SurveyAnswerRequest
import com.ic.surveyapi.answer.service.dto.SurveyAnswerDto
import com.ic.surveydata.form.entity.SurveyFormEntity
import survey.type.ItemType
import survey.type.ValidAnswerTypeLength

object InputParameterValidator {
    // SurveyItem 을 DB 에서 조회 하기 전 기본적인 벨리데이션
    fun SurveyAnswerRequest.SurveyItem.validateOrThrow() {
        when (this.type) {
            ItemType.SINGLE_SELECT, ItemType.MULTIPLE_SELECT -> {
                answer?.takeIf { it.isNotEmpty() }?.let {
                    throw IllegalArgumentException("유효한 파라미터가 아닙니다.")
                }
                selectedOptions.takeIf { it.isNotEmpty() } ?: throw IllegalArgumentException("유효한 파라미터가 아닙니다.")
            }

            ItemType.SHORT_ANSWER -> {
                answer?.takeIf { it.length < ValidAnswerTypeLength.VALID_SURVEY_SHORT_ANSWER_LENGTH }
                    ?: throw IllegalArgumentException("유효한 파라미터가 아닙니다.")
                selectedOptions.takeIf { it.isNotEmpty() }?.let {
                    throw IllegalArgumentException("유효한 파라미터가 아닙니다.")
                }
            }

            ItemType.LONG_ANSWER -> {
                answer?.takeIf { it.length >= ValidAnswerTypeLength.VALID_SURVEY_SHORT_ANSWER_LENGTH }
                    ?: throw IllegalArgumentException("유효한 파라미터가 아닙니다.")
                selectedOptions.takeIf { it.isNotEmpty() }?.let {
                    throw IllegalArgumentException("유효한 파라미터가 아닙니다.")
                }
            }

            else -> throw IllegalArgumentException("지원하지 않는 항목 유형입니다.")
        }
    }

    fun SurveyAnswerDto.validateOrThrow(surveyFormEntity: SurveyFormEntity) {
        this.surveyItems.forEach { inputSurveyItem ->
            // TODO - 위 벨리데이터와 중복을 제거하자 ... !
            val surveyItemEntity = surveyFormEntity.surveyItems.first { it.name == inputSurveyItem.name }
            if (surveyItemEntity.isRequired) {
                inputSurveyItem.answer.takeUnless { it.isNullOrBlank() }
                    ?: throw IllegalArgumentException("해당 답변은 필수 사항 입니다.")
            }

            // TODO - Validation 다시 고려해보기 !!
            val surveyEntityOptions = surveyItemEntity.surveyOptions
            inputSurveyItem.selectedOptions.forEach { option ->
                surveyEntityOptions.find { it.name == option } ?:
                    throw IllegalArgumentException("입력한 옵션이 유효하지 않습니다. $option")
            }
        }
    }
}
