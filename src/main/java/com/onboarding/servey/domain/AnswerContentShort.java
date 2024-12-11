package com.onboarding.servey.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("SHORT_TYPE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerContentShort extends AnswerContent {

	private String text;

	@Builder
	public AnswerContentShort(String text) {
		this.text = text;
	}
}
