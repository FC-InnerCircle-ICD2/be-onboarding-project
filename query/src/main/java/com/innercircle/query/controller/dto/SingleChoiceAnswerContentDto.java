package com.innercircle.query.controller.dto;

import com.innercircle.common.domain.survey.question.QuestionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SingleChoiceAnswerContentDto extends AnswerContentDto {

	private String selectedOption;

	public SingleChoiceAnswerContentDto(QuestionType type, String selectedOption) {
		super(type);
		this.selectedOption = selectedOption;
	}
}
