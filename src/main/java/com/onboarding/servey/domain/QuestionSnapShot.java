package com.onboarding.servey.domain;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.onboarding.servey.model.QuestionType;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionSnapShot {

	private Long questionId;
	private String name;
	private String description;
	@Enumerated(EnumType.STRING)
	private QuestionType type;
	private boolean required;

	@Builder
	public QuestionSnapShot(Long questionId, String name, String description, QuestionType type, boolean required) {
		this.questionId = questionId;
		this.name = name;
		this.description = description;
		this.type = type;
		this.required = required;
	}
}
