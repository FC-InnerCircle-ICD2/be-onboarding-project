package com.onboarding.servey.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnswerSingleResponse extends AnswerResponse {

	private String optionId;

	@Builder
	public AnswerSingleResponse(Long id, String optionId) {
		super(id);
		this.optionId = optionId;
	}
}
