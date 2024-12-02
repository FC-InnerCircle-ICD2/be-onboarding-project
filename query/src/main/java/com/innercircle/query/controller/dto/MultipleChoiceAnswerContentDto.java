package com.innercircle.query.controller.dto;

import com.innercircle.common.domain.survey.question.QuestionType;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MultipleChoiceAnswerContentDto extends AnswerContentDto {

	private static final int MAX_MULTIPLE_CHOICE_OPTIONS = 3;

	private List<String> selectedOptions;

	public MultipleChoiceAnswerContentDto(QuestionType type, List<String> selectedOptions) {
		super(type);
		this.selectedOptions = selectedOptions;
	}
}
