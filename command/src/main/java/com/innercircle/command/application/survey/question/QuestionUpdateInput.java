package com.innercircle.command.application.survey.question;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionUpdateInput {

	private String questionId;
	private QuestionInput question;

	public QuestionUpdateInput(String questionId, QuestionInput question) {
		this.questionId = questionId;
		this.question = question;
	}
}
