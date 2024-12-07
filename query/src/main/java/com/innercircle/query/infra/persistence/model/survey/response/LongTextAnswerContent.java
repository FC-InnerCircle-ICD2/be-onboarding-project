package com.innercircle.query.infra.persistence.model.survey.response;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@DiscriminatorValue("LONG_TEXT")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LongTextAnswerContent extends AnswerContent {

	private String text;

	public LongTextAnswerContent(String text) {
		this.text = text;
	}
}
