package com.innercircle.command.application.survey.response;

import com.innercircle.common.domain.survey.question.QuestionType;
import com.innercircle.common.domain.survey.response.AnswerContent;
import com.innercircle.common.domain.survey.response.LongTextAnswerContent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LongTextAnswerContentInput extends AnswerContentInput {

	private String text;

	public LongTextAnswerContentInput(QuestionType type, String text) {
		super(type);
		this.text = text;
	}

	@Override
	public AnswerContent convertToAnswerContent() {
		return new LongTextAnswerContent(this.getType(), this.text);
	}
}
