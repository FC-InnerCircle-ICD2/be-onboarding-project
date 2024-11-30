package survey.type

enum class ItemType(
    private val description: String,
) {
    SHORT_ANSWER(description = "단답형 설문 아이템"),
    LONG_ANSWER(description = "장문형 설문 아이템"),
    SINGLE_SELECT(description = "단일 선택 리스트"),
    MULTIPLE_SELECT(description = "다중 선택 리스트"),
}
