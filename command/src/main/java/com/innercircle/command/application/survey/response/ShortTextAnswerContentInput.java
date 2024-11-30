package com.innercircle.command.application.survey.response;

import com.innercircle.common.domain.survey.question.QuestionType;
import com.innercircle.common.domain.survey.response.AnswerContent;
import com.innercircle.common.domain.survey.response.ShortTextAnswerContent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShortTextAnswerContentInput extends AnswerContentInput {

	private String text;

	public ShortTextAnswerContentInput(QuestionType type, String text) {
		super(type);
		this.text = text;
	}

	@Override
	public AnswerContent convertToAnswerContent() {
		return new ShortTextAnswerContent(this.getType(), this.text);
	}
}
