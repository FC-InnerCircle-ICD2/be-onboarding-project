package com.onboarding.servey.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public abstract class AnswerResponse {

	private Long id;

	public AnswerResponse(Long id) {
		this.id = id;
	}
}
