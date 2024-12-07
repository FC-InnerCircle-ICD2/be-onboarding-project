package com.innercircle.command.application.survey;

import com.innercircle.common.application.Command;
import com.innercircle.command.application.survey.question.QuestionInput;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateSurveyCommand implements Command {

	private String name;
	private String description;
	private List<QuestionInput> questionInputs;

	public CreateSurveyCommand(String name, String description, List<QuestionInput> questionInputs) {
		this.name = name;
		this.description = description;
		this.questionInputs = questionInputs;
	}
}
