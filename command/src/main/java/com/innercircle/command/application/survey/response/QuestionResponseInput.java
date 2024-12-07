package com.innercircle.command.application.survey.response;

import com.innercircle.command.application.survey.question.QuestionInput;
import com.innercircle.command.domain.survey.question.Question;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionResponseInput {

	private String questionId;
	private QuestionInput question;
	private AnswerContentInput answerContent;

	public QuestionResponseInput(String questionId, QuestionInput question, AnswerContentInput answerContent) {
		this.questionId = questionId;
		this.question = question;
		this.answerContent = answerContent;
	}

	public Question convertToQuestion(String surveyId) {
		return this.question.convertToQuestion(this.questionId, surveyId);
	}
}
