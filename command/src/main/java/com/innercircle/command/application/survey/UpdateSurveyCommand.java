package com.innercircle.command.application.survey;

import com.innercircle.common.application.Command;
import com.innercircle.command.application.survey.question.QuestionUpdateInput;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateSurveyCommand implements Command {

	private String surveyId;
	private List<QuestionUpdateInput> questionUpdateInputs;

	public UpdateSurveyCommand(String surveyId, List<QuestionUpdateInput> questionUpdateInputs) {
		this.surveyId = surveyId;
		this.questionUpdateInputs = questionUpdateInputs;
	}
}
