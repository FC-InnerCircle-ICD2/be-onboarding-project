package com.innercircle.command.application.survey.question;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.innercircle.command.domain.survey.question.Question;
import com.innercircle.command.domain.survey.question.QuestionType;
import java.io.Serializable;
import java.util.UUID;
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
		@Type(value = ShortTextQuestionInput.class, name = "SHORT_TEXT"),
		@Type(value = LongTextQuestionInput.class, name = "LONG_TEXT"),
		@Type(value = SingleChoiceQuestionInput.class, name = "SINGLE_CHOICE"),
		@Type(value = MultipleChoiceQuestionInput.class, name = "MULTIPLE_CHOICE"),
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class QuestionInput implements Serializable {

	protected QuestionType type;
	protected boolean required;

	protected QuestionInput(QuestionType type, boolean required) {
		this.type = type;
		this.required = required;
	}

	public abstract Question convert(UUID surveyId);
}
