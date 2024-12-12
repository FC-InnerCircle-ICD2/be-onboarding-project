package com.onboarding.servey.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.onboarding.servey.domain.AnswerContent;
import com.onboarding.servey.model.QuestionType;

import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonTypeInfo(
	use = JsonTypeInfo.Id.NAME,
	include = As.EXISTING_PROPERTY,
	property = "type",
	visible = true
)
@JsonSubTypes({
	@Type(value = AnswerContentShortRequest.class, name = "SHORT_TYPE"),
	@Type(value = AnswerContentLongRequest.class, name = "LONG_TYPE"),
	@Type(value = AnswerContentSingleRequest.class, name = "SINGLE_LIST"),
	@Type(value = AnswerContentMultiRequest.class, name = "MULTI_LIST")
})
@Getter
@NoArgsConstructor
public abstract class AnswerRequest {

	protected QuestionType type;

	protected AnswerRequest(QuestionType type) {
		this.type = type;
	}

	public abstract AnswerContent convertToAnswerRequest();
}
