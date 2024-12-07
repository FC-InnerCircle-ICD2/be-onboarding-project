package com.innercircle.command.application.survey.response;

import com.innercircle.command.domain.survey.response.AnswerContent;
import com.innercircle.command.domain.survey.response.SingleChoiceAnswerContent;
import com.innercircle.common.domain.survey.question.QuestionType;
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
		return SingleChoiceAnswerContent.of(this.selectedOption);
	}
}
