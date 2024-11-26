package ziwookim.be_onboarding_project.research.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ResearchItemType {

    SHORT_ANSWER(1), // 단답형
    LONG_SENTENCE(2), // 장문형
    SINGLE_SELECTION(3), // 단일 선택형
    MULTIPLE_SELECTION(4); // 다중 선택형

    private final int itemType;
}
