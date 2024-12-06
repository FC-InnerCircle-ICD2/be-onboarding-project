package com.ic.surveyapi

import com.ic.surveyapi.answer.service.dto.SurveyAnswerDto
import com.ic.surveyapi.util.InputParameterValidator
import com.ic.surveydata.form.entity.SurveyItemEntity
import com.ic.surveydata.form.entity.SurveyOptionEntity
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import survey.exception.CustomErrorException
import survey.exception.CustomHttpStatusCode
import survey.type.ItemType
import survey.util.UuidGeneratorUtil

class SurveyAnswerValidationTest : StringSpec({

    "SINGLE_SELECT 항목에서 answer가 비어있지 않으면 CustomErrorException.InvalidAnswerFormat 예외 발생" {
        // Arrange
        val invalidSurveyItem =
            SurveyAnswerDto.SurveyItem(
                name = "아이템 1",
                type = ItemType.SINGLE_SELECT,
                answer = "유효하지 않은 답변",
                selectedOptions = listOf("2~3권"),
            )

        val surveyItemEntity =
            SurveyItemEntity(
                id = UuidGeneratorUtil.generateUuid(),
                name = "아이템 1",
                type = ItemType.SINGLE_SELECT,
                isRequired = true,
                description = "독서 권 수 조회 입니다.",
                surveyOptions =
                    mutableSetOf(
                        SurveyOptionEntity(
                            id = UuidGeneratorUtil.generateUuid(),
                            name = "2~3권",
                        ),
                        SurveyOptionEntity(
                            id = UuidGeneratorUtil.generateUuid(),
                            name = "1권 이하",
                        ),
                        SurveyOptionEntity(
                            id = UuidGeneratorUtil.generateUuid(),
                            name = "4권 이상",
                        ),
                    ),
            )

        val exception =
            shouldThrow<CustomErrorException.InvalidAnswerFormat> {
                InputParameterValidator.validateSingleSelect(invalidSurveyItem, surveyItemEntity)
            }

        exception.message shouldBe "SINGLE_SELECT 항목은 answer가 비어 있어야 합니다."
        exception.errorCode shouldBe CustomHttpStatusCode.BAD_REQUEST
    }

    "SINGLE_SELECT 항목에서 옵션이 여러 개 선택되면 CustomErrorException.InvalidOptionCount 예외 발생" {
        // Arrange
        val invalidSurveyItem =
            SurveyAnswerDto.SurveyItem(
                name = "아이템 1",
                type = ItemType.SINGLE_SELECT,
                answer = "",
                selectedOptions = listOf("2~3권", "1권 이하"),
            )
        val surveyItemEntity =
            SurveyItemEntity(
                id = UuidGeneratorUtil.generateUuid(),
                name = "아이템 1",
                type = ItemType.SINGLE_SELECT,
                isRequired = true,
                description = "",
                surveyOptions =
                    mutableSetOf(
                        SurveyOptionEntity(
                            id = UuidGeneratorUtil.generateUuid(),
                            name = "2~3권",
                        ),
                        SurveyOptionEntity(
                            id = UuidGeneratorUtil.generateUuid(),
                            name = "1권 이하",
                        ),
                        SurveyOptionEntity(
                            id = UuidGeneratorUtil.generateUuid(),
                            name = "4권 이상",
                        ),
                    ),
            )

        val exception =
            shouldThrow<CustomErrorException.InvalidAnswerFormat> {
                InputParameterValidator.validateSingleSelect(invalidSurveyItem, surveyItemEntity)
            }

        exception.message shouldBe "SINGLE_SELECT 항목은 옵션이 1개만 선택되어야 합니다."
        exception.errorCode shouldBe CustomHttpStatusCode.BAD_REQUEST
    }

    "SINGLE_SELECT 항목에서 올바른 입력이 들어오면 예외 발생하지 않음" {
        // Arrange
        val validSurveyItem =
            SurveyAnswerDto.SurveyItem(
                name = "아이템 1",
                type = ItemType.SINGLE_SELECT,
                answer = "",
                selectedOptions = listOf("2~3권"),
            )
        val surveyItemEntity =
            SurveyItemEntity(
                id = UuidGeneratorUtil.generateUuid(),
                name = "아이템 1",
                type = ItemType.SINGLE_SELECT,
                isRequired = true,
                description = "",
                surveyOptions =
                    mutableSetOf(
                        SurveyOptionEntity(id = UuidGeneratorUtil.generateUuid(), name = "2~3권"),
                        SurveyOptionEntity(id = UuidGeneratorUtil.generateUuid(), name = "1권 이하"),
                        SurveyOptionEntity(id = UuidGeneratorUtil.generateUuid(), name = "4권 이상"),
                    ),
            )

        InputParameterValidator.validateSingleSelect(validSurveyItem, surveyItemEntity)
    }
})
