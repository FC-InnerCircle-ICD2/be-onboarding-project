package com.innercircle.command.application.survey.question;

import com.innercircle.command.domain.survey.question.Question;
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

	public Question convertToQuestion(String questionId, String surveyId) {
		return this.question.convertToQuestion(questionId, surveyId);
	}
}
