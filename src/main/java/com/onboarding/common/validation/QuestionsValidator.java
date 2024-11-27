package com.onboarding.common.validation;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.onboarding.servey.dto.request.QuestionRequest;

public class QuestionsValidator implements ConstraintValidator<Questions, List<QuestionRequest>> {

	@Override
	public boolean isValid(List<QuestionRequest> questions, ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();

		for (QuestionRequest question : questions) {
			if (Arrays.asList("SINGLE_LIST", "MULTI_LIST").contains(question.getType()) && (question.getOptions() == null || question.getOptions().size() < 1)) {
				context.buildConstraintViolationWithTemplate("SINGLE_LIST(단일 선택 리스트) 또는 MULTI_LIST(다중 선택 리스트)인 경우 선택할 수 있는 후보를 포함하여야 합니다.")
					.addConstraintViolation();
				return false;
			} else if (Arrays.asList("SHORT_TYPE", "LONG_TYPE").contains(question.getType()) && (question.getOptions() != null && question.getOptions().size() > 0)) {
				context.buildConstraintViolationWithTemplate("SHORT_TYPE(단문형) 또는 LONG_TYPE(장문형)인 경우 선택할 수 있는 후보를 포함할 수 없습니다.")
					.addConstraintViolation();
				return false;
			}
		}
		return true;
	}
}
