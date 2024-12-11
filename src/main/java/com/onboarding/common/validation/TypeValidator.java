package com.onboarding.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.onboarding.servey.model.QuestionType;

public class TypeValidator implements ConstraintValidator<Type, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return QuestionType.of(value) != null;
	}
}
