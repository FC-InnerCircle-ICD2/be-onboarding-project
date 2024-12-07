package org.brinst.surveycore.answer.validator;

import org.brinst.surveycore.answer.dto.AnswerItemDTO;
import org.brinst.surveycore.survey.entity.SurveyQuestion;

public interface AnswerValidator {
	void validate(SurveyQuestion question, AnswerItemDTO answers);
}
