package com.innercircle.common.domain.survey.response;

import com.innercircle.common.domain.survey.question.QuestionType;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MultipleChoiceAnswerContent extends AnswerContent {

	private static final int MAX_MULTIPLE_CHOICE_OPTIONS = 3;

	private List<String> selectedOptions;

	public MultipleChoiceAnswerContent(QuestionType type, List<String> selectedOptions) {
		super(type);
		this.selectedOptions = selectedOptions;
	}

	@Override
	public void validate(boolean isRequired) {
		if ((isRequired && CollectionUtils.isEmpty(this.selectedOptions)) || CollectionUtils.size(this.selectedOptions) > MAX_MULTIPLE_CHOICE_OPTIONS) {
			throw new IllegalArgumentException("Multiple choice question must have between 1 and %d options selected".formatted(MAX_MULTIPLE_CHOICE_OPTIONS));
		}
	}
}
