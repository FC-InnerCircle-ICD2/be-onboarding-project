package com.innercircle.query.controller.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.innercircle.common.domain.survey.question.QuestionType;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = As.EXISTING_PROPERTY,
		property = "type",
		visible = true
)
@JsonSubTypes({
		@Type(value = ShortTextAnswerContentDto.class, name = "SHORT_TEXT"),
		@Type(value = LongTextAnswerContentDto.class, name = "LONG_TEXT"),
		@Type(value = SingleChoiceAnswerContentDto.class, name = "SINGLE_CHOICE"),
		@Type(value = MultipleChoiceAnswerContentDto.class, name = "MULTIPLE_CHOICE"),
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AnswerContentDto implements Serializable {

	protected QuestionType type;

	protected AnswerContentDto(QuestionType type) {
		this.type = type;
	}
}
