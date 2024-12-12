package com.onboarding.servey.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnswerLongResponse extends AnswerResponse {

	private String text;

	@Builder
	public AnswerLongResponse(Long id, String text) {
		super(id);
		this.text = text;
	}
}
