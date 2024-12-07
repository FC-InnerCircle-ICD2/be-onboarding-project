package com.innercircle.command.domain.survey.response;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@DiscriminatorValue("LONG_TEXT")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LongTextAnswerContent extends AnswerContent {

	private static final int MAX_LONG_TEXT_LENGTH = 50;

	private String text;

	LongTextAnswerContent(String text) {
		super();
		this.text = text;
	}

	public static LongTextAnswerContent of(String text) {
		return new LongTextAnswerContent(text);
	}

	public void validate(boolean isRequired) {
		if ((isRequired && StringUtils.isBlank(this.text)) || StringUtils.length(this.text) > MAX_LONG_TEXT_LENGTH) {
			throw new IllegalArgumentException("Long text answer must not be empty and must not exceed %d characters".formatted(MAX_LONG_TEXT_LENGTH));
		}
	}
}
