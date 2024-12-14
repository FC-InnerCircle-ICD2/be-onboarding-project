package com.onboarding.servey.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionReplyResponse {

	private Long serveyId;
	private List<QuestionResponse> questions;

	@Builder
	public QuestionReplyResponse(Long serveyId, List<QuestionResponse> questions) {
		this.serveyId = serveyId;
		this.questions = questions;
	}
}
