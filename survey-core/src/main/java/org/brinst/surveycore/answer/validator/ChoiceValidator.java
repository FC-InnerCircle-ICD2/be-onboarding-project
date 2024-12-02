package org.brinst.surveycore.answer.validator;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.brinst.surveycommon.enums.ErrorCode;
import org.brinst.surveycommon.enums.OptionType;
import org.brinst.surveycommon.exception.GlobalException;
import org.brinst.surveycore.survey.entity.SurveyOption;
import org.brinst.surveycore.survey.entity.SurveyQuestion;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class ChoiceValidator implements AnswerValidator {

	@Override
	public void validate(SurveyQuestion question, List<String> answers) {
		Set<String> validOptions = question.getSurveyOptions().stream()
			.map(SurveyOption::getOption)
			.collect(Collectors.toSet());

		// 필수값 검증
		if (question.isRequired() && CollectionUtils.isEmpty(answers)) {
			throw new GlobalException(ErrorCode.BAD_REQUEST, "Answer is required.");
		}

		// 단일 선택 검증
		if (question.getOptionType() == OptionType.SINGLE_CHOICE) {
			if (answers.size() != 1 || !validOptions.contains(answers.get(0))) {
				throw new GlobalException(ErrorCode.BAD_REQUEST, "Invalid single choice answer.");
			}
		}

		// 다중 선택 검증
		if (question.getOptionType() == OptionType.MULTIPLE_CHOICE) {
			if ((answers.isEmpty() || !validOptions.containsAll(answers))) {
				throw new GlobalException(ErrorCode.BAD_REQUEST, "Invalid multiple choice answers.");
			}
		}
	}
}
