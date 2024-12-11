package com.onboarding.servey.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("SINGLE_LIST")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerContentSingle extends AnswerContent {

	private String optionId;

	@Builder
	public AnswerContentSingle(String optionId) {
		this.optionId = optionId;
	}
}
