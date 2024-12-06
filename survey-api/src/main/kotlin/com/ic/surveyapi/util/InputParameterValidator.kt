package com.ic.surveyapi.util

import com.ic.surveyapi.answer.service.dto.SurveyAnswerDto
import com.ic.surveydata.form.entity.SurveyFormEntity
import com.ic.surveydata.form.entity.SurveyItemEntity
import com.ic.surveydata.form.entity.SurveyOptionEntity
import survey.exception.CustomErrorException
import survey.exception.CustomHttpStatusCode
import survey.type.ItemType

object InputParameterValidator {
    fun SurveyAnswerDto.validateOrThrow(surveyFormEntity: SurveyFormEntity) {
        val entitySurveyItemNames = surveyFormEntity.surveyItems.map { it.name }.toSet()
        val inputSurveyItemNames = this.surveyItems.map { it.name }.toSet()

        // 1. 입력된 SurveyItems의 이름이 SurveyFormEntity와 일치하는지 확인
        if ((inputSurveyItemNames - entitySurveyItemNames).isNotEmpty()) {
            throw CustomErrorException.NotMatchSurveyFormItem(errorCode = CustomHttpStatusCode.BAD_REQUEST)
        }

        // 2. 각 SurveyItem에 대해 개별적으로 벨리데이션 수행
        this.surveyItems.forEach { inputSurveyItem ->
            val surveyItemEntity = surveyFormEntity.surveyItems.first { it.name == inputSurveyItem.name }

            // 2-1. 필수 항목 검사
            validateRequiredField(inputSurveyItem, surveyItemEntity)

            // 2-2. 타입별 벨리데이션 수행
            when (inputSurveyItem.type) {
                ItemType.SINGLE_SELECT -> validateSingleSelect(inputSurveyItem, surveyItemEntity)
                ItemType.MULTIPLE_SELECT -> validateMultipleSelect(inputSurveyItem, surveyItemEntity)
                ItemType.SHORT_ANSWER -> validateShortAnswer(inputSurveyItem)
                ItemType.LONG_ANSWER -> validateLongAnswer(inputSurveyItem)
            }
        }
    }

    // 필수 항목 공통 검사
    private fun validateRequiredField(
        inputSurveyItem: SurveyAnswerDto.SurveyItem,
        surveyItemEntity: SurveyItemEntity,
    ) {
        if (surveyItemEntity.isRequired && inputSurveyItem.answer.isNullOrBlank() && inputSurveyItem.selectedOptions.isEmpty()) {
            throw CustomErrorException.RequiredAnswer(errorCode = CustomHttpStatusCode.BAD_REQUEST)
        }
    }

    // SINGLE_SELECT 벨리데이션
    fun validateSingleSelect(
        inputSurveyItem: SurveyAnswerDto.SurveyItem,
        surveyItemEntity: SurveyItemEntity,
    ) {
        if (!inputSurveyItem.answer.isNullOrBlank()) {
            throw CustomErrorException.InvalidAnswerFormat(
                message = "SINGLE_SELECT 항목은 answer가 비어 있어야 합니다.",
                errorCode = CustomHttpStatusCode.BAD_REQUEST,
            )
        }
        if (inputSurveyItem.selectedOptions.size != 1) {
            throw CustomErrorException.InvalidAnswerFormat(
                message = "SINGLE_SELECT 항목은 옵션이 1개만 선택되어야 합니다.",
                errorCode = CustomHttpStatusCode.BAD_REQUEST,
            )
        }
        validateOptions(inputSurveyItem.selectedOptions, surveyItemEntity.surveyOptions)
    }

    // MULTIPLE_SELECT 벨리데이션
    private fun validateMultipleSelect(
        inputSurveyItem: SurveyAnswerDto.SurveyItem,
        surveyItemEntity: SurveyItemEntity,
    ) {
        if (!inputSurveyItem.answer.isNullOrBlank()) {
            throw CustomErrorException.InvalidAnswerFormat(
                message = "MULTIPLE_SELECT 항목은 answer가 비어 있어야 합니다.",
                errorCode = CustomHttpStatusCode.BAD_REQUEST,
            )
        }
        validateOptions(inputSurveyItem.selectedOptions, surveyItemEntity.surveyOptions)
    }

    // SHORT_ANSWER 벨리데이션
    private fun validateShortAnswer(inputSurveyItem: SurveyAnswerDto.SurveyItem) {
        if (inputSurveyItem.answer.isNullOrBlank()) {
            throw CustomErrorException.RequiredAnswer(errorCode = CustomHttpStatusCode.BAD_REQUEST)
        }
        if (inputSurveyItem.answer.length > 100) {
            throw CustomErrorException.InvalidAnswerFormat(
                message = "SHORT_ANSWER 항목은 100자를 초과할 수 없습니다.",
                errorCode = CustomHttpStatusCode.BAD_REQUEST,
            )
        }
        if (inputSurveyItem.selectedOptions.isNotEmpty()) {
            throw CustomErrorException.InvalidAnswerFormat(
                message = "SHORT_ANSWER 항목은 옵션을 선택할 수 없습니다.",
                errorCode = CustomHttpStatusCode.BAD_REQUEST,
            )
        }
    }

    // LONG_ANSWER 벨리데이션
    private fun validateLongAnswer(inputSurveyItem: SurveyAnswerDto.SurveyItem) {
        if (inputSurveyItem.answer.isNullOrBlank()) {
            throw CustomErrorException.RequiredAnswer(errorCode = CustomHttpStatusCode.BAD_REQUEST)
        }
        if (inputSurveyItem.answer.length <= 100) {
            throw CustomErrorException.InvalidAnswerFormat(
                message = "LONG_ANSWER 항목은 100자 이상이어야 합니다.",
                errorCode = CustomHttpStatusCode.BAD_REQUEST,
            )
        }
        if (inputSurveyItem.selectedOptions.isNotEmpty()) {
            throw CustomErrorException.InvalidAnswerFormat(
                message = "LONG_ANSWER 항목은 옵션을 선택할 수 없습니다.",
                errorCode = CustomHttpStatusCode.BAD_REQUEST,
            )
        }
    }

    // 공통 옵션 유효성 검사
    private fun validateOptions(
        selectedOptions: List<String>,
        validOptions: Set<SurveyOptionEntity>,
    ) {
        selectedOptions.forEach { option ->
            if (validOptions.none { it.name == option }) {
                throw CustomErrorException.InvalidAnswerFormat(
                    message = "유효하지 않은 옵션입니다: $option",
                    errorCode = CustomHttpStatusCode.BAD_REQUEST,
                )
            }
        }
    }
}
