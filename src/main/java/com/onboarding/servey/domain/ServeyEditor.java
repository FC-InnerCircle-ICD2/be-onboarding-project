package com.onboarding.servey.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ServeyEditor {

	private String name;
	private String description;

	@Builder
	public ServeyEditor(String name, String description) {
		this.name = name;
		this.description = description;
	}
}
