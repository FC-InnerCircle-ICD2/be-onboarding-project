package com.onboarding.servey.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("LONG_TYPE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerContentLong extends AnswerContent {

	private String text;

	@Builder
	public AnswerContentLong(String text) {
		this.text = text;
	}
}
