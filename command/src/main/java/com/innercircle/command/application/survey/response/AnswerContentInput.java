package com.innercircle.command.application.survey.response;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.innercircle.common.domain.survey.question.QuestionType;
import com.innercircle.common.domain.survey.response.AnswerContent;
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
		@Type(value = ShortTextAnswerContentInput.class, name = "SHORT_TEXT"),
		@Type(value = LongTextAnswerContentInput.class, name = "LONG_TEXT"),
		@Type(value = SingleChoiceAnswerContentInput.class, name = "SINGLE_CHOICE"),
		@Type(value = MultipleChoiceAnswerContentInput.class, name = "MULTIPLE_CHOICE"),
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AnswerContentInput implements Serializable {

	protected QuestionType type;

	protected AnswerContentInput(QuestionType type) {
		this.type = type;
	}

	public abstract AnswerContent convertToAnswerContent();
}
