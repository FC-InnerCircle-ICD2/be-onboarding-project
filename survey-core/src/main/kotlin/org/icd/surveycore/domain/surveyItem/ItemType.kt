package org.icd.surveycore.domain.surveyItem

enum class ItemType(val description: String) {
    SHORT_ANSWER("단답형"),
    LONG_ANSWER("장문형"),
    SINGLE_CHOICE("단일 선택 리스트"),
    MULTIPLE_CHOICE("다중 선택 리스트");
}
