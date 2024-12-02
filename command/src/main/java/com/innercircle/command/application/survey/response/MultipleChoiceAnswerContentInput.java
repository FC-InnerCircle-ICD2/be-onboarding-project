package com.innercircle.command.application.survey.response;

import com.innercircle.common.domain.survey.question.QuestionType;
import com.innercircle.common.domain.survey.response.AnswerContent;
import com.innercircle.common.domain.survey.response.MultipleChoiceAnswerContent;
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
		return new MultipleChoiceAnswerContent(this.getType(), this.selectedOptions);
	}
}
