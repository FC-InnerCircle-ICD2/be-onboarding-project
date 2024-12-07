package com.metsakurr.beonboardingproject.common.validation;

import com.metsakurr.beonboardingproject.domain.survey.entity.QuestionType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class QuestionTypeValidator implements ConstraintValidator<ValidQuestionType, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            QuestionType.valueOf(value);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}