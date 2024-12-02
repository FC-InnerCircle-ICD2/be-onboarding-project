package com.innercircle.query.controller.dto;

import com.innercircle.common.domain.survey.question.QuestionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LongTextAnswerContentDto extends AnswerContentDto {

	private String text;

	public LongTextAnswerContentDto(QuestionType type, String text) {
		super(type);
		this.text = text;
	}
}
