package com.onboarding.beonboardingproject.domain.survey;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum QuestionType {
    SHORT_ANSWER("단답형"),
    LONG_ANSWER("장문형"),
    SINGLE_CHOICE("단일 선택 리스트"),
    MULTIPLE_CHOICE("다중 선택 리스트");

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
        throw new IllegalArgumentException("Invalid display name");

    }

    public static boolean isValidName(String name) {
        for (QuestionType type : values()) {
            if (type.name.equals(name)) {
                return true;
            }
        }
        return false;
    }
}
