package org.brinst.surveycore.answer.validator;

import java.util.List;

import org.brinst.surveycore.survey.entity.SurveyQuestion;

public interface AnswerValidator {
	void validate(SurveyQuestion question, List<String> answers);
}
