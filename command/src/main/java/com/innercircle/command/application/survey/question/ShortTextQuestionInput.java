package com.innercircle.command.application.survey.question;

import com.innercircle.command.domain.survey.question.Question;
import com.innercircle.command.domain.survey.question.QuestionType;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShortTextQuestionInput extends QuestionInput {

	private String name;
	private String description;

	public ShortTextQuestionInput(QuestionType type, boolean required, String name, String description) {
		super(type, required);
		this.name = name;
		this.description = description;
	}

	@Override
	public Question convertToQuestion(String questionId, String surveyId) {
		return new Question(questionId, surveyId, this.name, this.description, this.required, this.type, List.of());
	}
}
