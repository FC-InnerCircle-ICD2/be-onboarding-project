package com.onboarding.servey.dto.request;

import com.onboarding.servey.domain.AnswerContent;
import com.onboarding.servey.domain.AnswerContentShort;
import com.onboarding.servey.model.QuestionType;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnswerContentShortRequest extends AnswerRequest {

	private String text;

	public AnswerContentShortRequest(QuestionType type, String text) {
		super(type);
		this.text = text;
	}

	@Override
	public AnswerContent convertToAnswerRequest() {
		return AnswerContentShort.builder()
			.text(text)
			.build();
	}
}
