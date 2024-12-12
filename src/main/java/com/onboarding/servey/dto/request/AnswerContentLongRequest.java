package com.onboarding.servey.dto.request;

import com.onboarding.servey.domain.AnswerContent;
import com.onboarding.servey.domain.AnswerContentLong;
import com.onboarding.servey.model.QuestionType;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnswerContentLongRequest extends AnswerRequest {

	private String text;

	public AnswerContentLongRequest(QuestionType type, String text) {
		super(type);
		this.text = text;
	}

	@Override
	public AnswerContent convertToAnswerRequest() {
		return AnswerContentLong.builder()
			.text(text)
			.build();
	}
}
