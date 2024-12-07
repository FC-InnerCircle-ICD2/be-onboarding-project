package org.brinst.surveycore.answer.validator;

import org.brinst.surveycommon.enums.ErrorCode;
import org.brinst.surveycommon.exception.GlobalException;
import org.brinst.surveycore.answer.dto.AnswerItemDTO;
import org.brinst.surveycore.survey.entity.SurveyQuestion;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class RequireAnswerValidator implements AnswerValidator {

	@Override
	public void validate(SurveyQuestion question, AnswerItemDTO answers) {
		// 필수 응답 여부 검증
		if (question.isRequired() && isAnswerEmpty(answers)) {
			throw new GlobalException(ErrorCode.BAD_REQUEST, "Answer is required but not provided.");
		}
	}

	private boolean isAnswerEmpty(AnswerItemDTO answers) {
		if (answers == null) {
			return true;
		}

		// 타입별로 응답 값 검증
		if (answers instanceof AnswerItemDTO.ShortAnswer shortAnswer) {
			return shortAnswer.getAnswer() == null || shortAnswer.getAnswer().isBlank();
		}

		if (answers instanceof AnswerItemDTO.LongAnswer longAnswer) {
			return longAnswer.getAnswer() == null || longAnswer.getAnswer().isBlank();
		}

		if (answers instanceof AnswerItemDTO.SingleChoice singleChoice) {
			return singleChoice.getOption() == null || singleChoice.getOption().isBlank();
		}

		if (answers instanceof AnswerItemDTO.MultiChoice multiChoice) {
			return CollectionUtils.isEmpty(multiChoice.getOptions())
				|| multiChoice.getOptions().stream().anyMatch(option -> option == null || option.isBlank());
		}

		// 기본적으로 null이 아닌 경우 값이 존재하는 것으로 간주
		return false;
	}
}
