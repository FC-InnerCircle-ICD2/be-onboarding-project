package com.onboarding.servey.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QuestionType {
	SHORT_TYPE("단답형"),
	LONG_TYPE("장문형"),
	SINGLE_LIST("단일 선택 리스트"),
	MULTI_LIST("다중 선택 리스트");

	private final String name;

	public static QuestionType of(String name) {
		for (QuestionType type : QuestionType.values()) {
			if (type.name().equals(name)) {
				return type;
			}
		}
		return null;
	}
}
