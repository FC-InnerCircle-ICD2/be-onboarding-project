package com.innercircle.command.application.survey.response;

import com.innercircle.common.domain.survey.question.QuestionType;
import com.innercircle.common.domain.survey.response.AnswerContent;
import com.innercircle.common.domain.survey.response.SingleChoiceAnswerContent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SingleChoiceAnswerContentInput extends AnswerContentInput {

	private String selectedOption;

	public SingleChoiceAnswerContentInput(QuestionType type, String selectedOption) {
		super(type);
		this.selectedOption = selectedOption;
	}

	@Override
	public AnswerContent convertToAnswerContent() {
		return new SingleChoiceAnswerContent(this.getType(), this.selectedOption);
	}
}
