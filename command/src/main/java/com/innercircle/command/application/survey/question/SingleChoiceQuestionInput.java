package com.innercircle.command.application.survey.question;

import com.innercircle.command.domain.survey.question.Question;
import com.innercircle.command.domain.survey.question.QuestionType;
import com.innercircle.command.infra.persistence.generator.IdGenerator;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SingleChoiceQuestionInput extends QuestionInput {

	private String name;
	private String description;
	private List<String> optionNames;

	public SingleChoiceQuestionInput(QuestionType type, boolean required, String name, String description, List<String> optionNames) {
		super(type, required);
		this.name = name;
		this.description = description;
		this.optionNames = optionNames;
	}

	@Override
	public Question convert(UUID surveyId) {
		return new Question(IdGenerator.generate(), surveyId, this.name, this.description, this.required, QuestionType.SINGLE_CHOICE, this.optionNames);
	}
}
