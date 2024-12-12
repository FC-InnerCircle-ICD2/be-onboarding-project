package com.onboarding.servey.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnswerShortResponse extends AnswerResponse {

	private String text;

	@Builder
	public AnswerShortResponse(Long id, String text) {
		super(id);
		this.text = text;
	}
}
