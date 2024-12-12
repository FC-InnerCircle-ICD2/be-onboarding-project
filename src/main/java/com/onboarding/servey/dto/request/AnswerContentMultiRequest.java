package com.onboarding.servey.dto.request;

import java.util.List;

import com.onboarding.servey.domain.AnswerContent;
import com.onboarding.servey.domain.AnswerContentMulti;
import com.onboarding.servey.model.QuestionType;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnswerContentMultiRequest extends AnswerRequest {

	private List<String> optionIds;

	public AnswerContentMultiRequest(QuestionType type, List<String> optionId) {
		super(type);
		this.optionIds = optionId;
	}

	@Override
	public AnswerContent convertToAnswerRequest() {
		return AnswerContentMulti.builder()
			.optionId(optionIds)
			.build();
	}
}
