package com.onboarding.servey.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OptionEditor {

	private int number;

	@Builder
	public OptionEditor(int number) {
		this.number = number;
	}
}
