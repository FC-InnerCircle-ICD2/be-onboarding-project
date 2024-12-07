package com.innercircle.command.domain.survey.response;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@DiscriminatorValue("SHORT_TEXT")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShortTextAnswerContent extends AnswerContent {

	private static final int MAX_SHORT_TEXT_LENGTH = 20;

	private String text;

	ShortTextAnswerContent(String text) {
		this.text = text;
	}

	public static ShortTextAnswerContent of(String text) {
		return new ShortTextAnswerContent(text);
	}

	@Override
	public void validate(boolean isRequired) {
		if ((isRequired && StringUtils.isBlank(this.text)) || StringUtils.length(this.text) > MAX_SHORT_TEXT_LENGTH) {
			throw new IllegalArgumentException("Short text answer must not be empty and must not exceed %d characters".formatted(MAX_SHORT_TEXT_LENGTH));
		}
	}
}
