package com.metsakurr.beonboardingproject.common.validation;

import com.metsakurr.beonboardingproject.domain.survey.entity.QuestionType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class QuestionTypeValidator implements ConstraintValidator<ValidQuestionType, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        return QuestionType.isValidName(value);
    }
}