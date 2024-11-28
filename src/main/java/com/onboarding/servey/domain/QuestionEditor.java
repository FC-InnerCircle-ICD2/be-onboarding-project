package com.onboarding.servey.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class QuestionEditor {

	private String name;
	private String description;
	private QuestionType type;
	private boolean required;

	@Builder
	public QuestionEditor(String name, String description, QuestionType type, boolean required) {
		this.name = name;
		this.description = description;
		this.type = type;
		this.required = required;
	}
}
