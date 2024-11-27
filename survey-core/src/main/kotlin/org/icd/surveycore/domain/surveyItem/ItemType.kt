package org.icd.surveycore.domain.surveyItem

import org.icd.surveycore.domain.support.value.BaseEnum

enum class ItemType(
    override val description: String
) : BaseEnum {
    SHORT_ANSWER("단답형"),
    LONG_ANSWER("장문형"),
    SINGLE_CHOICE("단일 선택 리스트"),
    MULTIPLE_CHOICE("다중 선택 리스트");
}
