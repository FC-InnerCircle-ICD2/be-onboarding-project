package com.innercircle.command.application.survey;

import com.innercircle.common.application.Command;
import com.innercircle.command.application.survey.response.QuestionResponseInput;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateSurveyResponseCommand implements Command {

	private String surveyId;
	private List<QuestionResponseInput> responseInputs;

	public CreateSurveyResponseCommand(String surveyId, List<QuestionResponseInput> responseInputs) {
		this.surveyId = surveyId;
		this.responseInputs = responseInputs;
	}
}
