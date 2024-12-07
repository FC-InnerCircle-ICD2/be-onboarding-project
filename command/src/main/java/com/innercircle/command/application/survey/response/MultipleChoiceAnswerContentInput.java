package com.innercircle.command.application.survey.response;

import com.innercircle.command.domain.survey.response.AnswerContent;
import com.innercircle.command.domain.survey.response.MultipleChoiceAnswerContent;
import com.innercircle.common.domain.survey.question.QuestionType;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MultipleChoiceAnswerContentInput extends AnswerContentInput {

	private List<String> selectedOptions;

	public MultipleChoiceAnswerContentInput(QuestionType type, List<String> selectedOptions) {
		super(type);
		this.selectedOptions = selectedOptions;
	}

	@Override
	public AnswerContent convertToAnswerContent() {
		return MultipleChoiceAnswerContent.of(this.selectedOptions);
	}
}
