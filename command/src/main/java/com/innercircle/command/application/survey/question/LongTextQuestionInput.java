package com.innercircle.command.application.survey.question;

import com.innercircle.command.domain.survey.question.QuestionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LongTextQuestionInput extends QuestionInput {

	private String name;
	private String description;

	public LongTextQuestionInput(QuestionType type, boolean required, String name, String description) {
		super(type, required);
		this.name = name;
		this.description = description;
	}
}
