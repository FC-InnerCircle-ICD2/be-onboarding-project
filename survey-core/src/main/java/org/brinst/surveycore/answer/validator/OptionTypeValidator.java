package org.brinst.surveycore.answer.validator;

import java.util.List;

import org.brinst.surveycommon.enums.ErrorCode;
import org.brinst.surveycommon.enums.OptionType;
import org.brinst.surveycommon.exception.GlobalException;
import org.brinst.surveycore.survey.entity.SurveyQuestion;
import org.springframework.stereotype.Component;

@Component
public class OptionTypeValidator implements AnswerValidator {

	@Override
	public void validate(SurveyQuestion question, List<String> answers) {
		if (question.getOptionType() != OptionType.MULTIPLE_CHOICE && answers.size() > 1) {
			throw new GlobalException(ErrorCode.BAD_REQUEST);
		}
	}
}
