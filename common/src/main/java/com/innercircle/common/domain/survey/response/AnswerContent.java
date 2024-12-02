package com.innercircle.common.domain.survey.response;

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
		@Type(value = ShortTextAnswerContent.class, name = "SHORT_TEXT"),
		@Type(value = LongTextAnswerContent.class, name = "LONG_TEXT"),
		@Type(value = SingleChoiceAnswerContent.class, name = "SINGLE_CHOICE"),
		@Type(value = MultipleChoiceAnswerContent.class, name = "MULTIPLE_CHOICE"),
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AnswerContent implements Serializable {

	protected QuestionType type;

	protected AnswerContent(QuestionType type) {
		this.type = type;
	}

	public abstract void validate(boolean isRequired);
}
