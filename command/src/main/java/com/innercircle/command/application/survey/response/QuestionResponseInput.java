package com.innercircle.command.application.survey.response;

import com.innercircle.command.application.survey.question.QuestionInput;
import com.innercircle.command.domain.survey.question.Question;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionResponseInput {

	private String questionId;
	private QuestionInput question;
	private String text;
	private List<String> selectedOptions;

	public QuestionResponseInput(String questionId, QuestionInput question, String text, List<String> selectedOptions) {
		this.questionId = questionId;
		this.question = question;
		this.text = text;
		this.selectedOptions = selectedOptions;
	}

	public Question convertToQuestion(String surveyId) {
		return this.question.convertToQuestion(this.questionId, surveyId);
	}
}
