package com.metsakurr.beonboardingproject.domain.survey.entity;

public enum QuestionType {
    SHORT_SENTENCE("단답형"),
    LONG_SENTENCE("장문형"),
    SINGLE_CHOICE("단일 선택 리스트"),
    MULTI_CHOICE("다중 선택 리스트");

    private final String name;

    QuestionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static QuestionType fromName(String name) {
        for (QuestionType type : values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid display name"); // TODO: custom exception and handling
    }
}
