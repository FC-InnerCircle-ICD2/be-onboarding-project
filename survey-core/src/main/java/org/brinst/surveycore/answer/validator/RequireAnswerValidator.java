package org.brinst.surveycore.answer.validator;

import java.util.List;

import org.brinst.surveycommon.enums.ErrorCode;
import org.brinst.surveycommon.exception.GlobalException;
import org.brinst.surveycore.survey.entity.SurveyQuestion;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class RequireAnswerValidator implements AnswerValidator {

	@Override
	public void validate(SurveyQuestion question, List<String> answers) {
		if (question.isRequired() && CollectionUtils.isEmpty(answers)) {
			throw new GlobalException(ErrorCode.BAD_REQUEST);
		}
	}
}
