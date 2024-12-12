package com.onboarding.servey.dto.request;

import com.onboarding.servey.domain.AnswerContent;
import com.onboarding.servey.domain.AnswerContentSingle;
import com.onboarding.servey.model.QuestionType;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnswerContentSingleRequest extends AnswerRequest {

	private String optionId;

	public AnswerContentSingleRequest(QuestionType type, String optionId) {
		super(type);
		this.optionId = optionId;
	}

	@Override
	public AnswerContent convertToAnswerRequest() {
		return AnswerContentSingle.builder()
			.optionId(optionId)
			.build();
	}
}
