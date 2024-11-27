package com.onboarding.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.onboarding.servey.domain.QuestionType;

public class TypeValidator implements ConstraintValidator<Type, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return QuestionType.contain(value) != null;
	}
}
