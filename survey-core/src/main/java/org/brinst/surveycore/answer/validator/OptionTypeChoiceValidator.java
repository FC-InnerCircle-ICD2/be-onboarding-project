package org.brinst.surveycore.answer.validator;

import java.util.Set;
import java.util.stream.Collectors;

import org.brinst.surveycommon.enums.ErrorCode;
import org.brinst.surveycommon.exception.GlobalException;
import org.brinst.surveycore.answer.dto.AnswerItemDTO;
import org.brinst.surveycore.survey.entity.SurveyOption;
import org.brinst.surveycore.survey.entity.SurveyQuestion;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class OptionTypeChoiceValidator implements AnswerValidator {

	@Override
	public void validate(SurveyQuestion question, AnswerItemDTO answers) {
		// 필수 응답 여부 검증
		validateRequired(question, answers);

		// 타입에 맞는 선택 검증
		switch (question.getOptionType()) {
			case SHORT_ANSWER, LONG_ANSWER -> {
				// SHORT_ANSWER와 LONG_ANSWER는 검증하지 않음
			}
			case SINGLE_CHOICE -> validateSingleChoice(question, answers);
			case MULTIPLE_CHOICE -> validateMultipleChoice(question, answers);
			default -> throw new GlobalException(ErrorCode.BAD_REQUEST, "Unsupported option type.");
		}
	}

	private void validateRequired(SurveyQuestion question, AnswerItemDTO answers) {
		if (question.isRequired() && isAnswerEmpty(answers)) {
			throw new GlobalException(ErrorCode.BAD_REQUEST, "Answer is required.");
		}
	}

	private void validateSingleChoice(SurveyQuestion question, AnswerItemDTO answers) {
		if (!(answers instanceof AnswerItemDTO.SingleChoice singleChoice)) {
			throw new GlobalException(ErrorCode.BAD_REQUEST, "Invalid answer type for single choice.");
		}

		Set<String> validOptions = extractValidOptions(question);
		if (!validOptions.contains(singleChoice.getOption())) {
			throw new GlobalException(ErrorCode.BAD_REQUEST, "Invalid single choice answer.");
		}
	}

	private void validateMultipleChoice(SurveyQuestion question, AnswerItemDTO answers) {
		if (!(answers instanceof AnswerItemDTO.MultiChoice multiChoice)) {
			throw new GlobalException(ErrorCode.BAD_REQUEST, "Invalid answer type for multiple choice.");
		}

		Set<String> validOptions = extractValidOptions(question);
		if (multiChoice.getOptions() == null || multiChoice.getOptions().isEmpty() ||
			!validOptions.containsAll(multiChoice.getOptions())) {
			throw new GlobalException(ErrorCode.BAD_REQUEST, "Invalid multiple choice answers.");
		}
	}

	private Set<String> extractValidOptions(SurveyQuestion question) {
		return question.getSurveyOptions().stream()
			.map(SurveyOption::getOption)
			.collect(Collectors.toSet());
	}

	// 공통적인 답변 비어있는지 확인하는 메서드
	private boolean isAnswerEmpty(AnswerItemDTO answers) {
		if (answers == null) {
			return true;
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

