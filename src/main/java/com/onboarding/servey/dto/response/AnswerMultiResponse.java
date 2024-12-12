package com.onboarding.servey.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnswerMultiResponse extends AnswerResponse {

	private List<String> optionIds;

	@Builder
	public AnswerMultiResponse(Long id, List<String> optionIds) {
		super(id);
		this.optionIds = optionIds;
	}
}
