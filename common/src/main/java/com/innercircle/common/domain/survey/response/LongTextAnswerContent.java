package com.innercircle.common.domain.survey.response;

import com.innercircle.common.domain.survey.question.QuestionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LongTextAnswerContent extends AnswerContent {

	private static final int MAX_LONG_TEXT_LENGTH = 50;

	private String text;

	public LongTextAnswerContent(QuestionType type, String text) {
		super(type);
		this.text = text;
	}

	public void validate(boolean isRequired) {
		if ((isRequired && StringUtils.isBlank(this.text)) || StringUtils.length(this.text) > MAX_LONG_TEXT_LENGTH) {
			throw new IllegalArgumentException("Long text answer must not be empty and must not exceed %d characters".formatted(MAX_LONG_TEXT_LENGTH));
		}
	}
}
