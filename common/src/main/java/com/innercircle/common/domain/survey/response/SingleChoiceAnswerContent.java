package com.innercircle.common.domain.survey.response;

import com.innercircle.common.domain.survey.question.QuestionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SingleChoiceAnswerContent extends AnswerContent {

	private String selectedOption;

	public SingleChoiceAnswerContent(QuestionType type, String selectedOption) {
		super(type);
		this.selectedOption = selectedOption;
	}

	@Override
	public void validate(boolean isRequired) {
		if (isRequired && StringUtils.isBlank(this.selectedOption)) {
			throw new IllegalArgumentException("Single choice answer must not be empty");
		}
	}
}
