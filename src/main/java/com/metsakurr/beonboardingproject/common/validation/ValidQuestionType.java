package com.metsakurr.beonboardingproject.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = QuestionTypeValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidQuestionType {
    String message() default "[항목 입력 형태]는 [SHORT_SENTENCE], [LONG_SENTENCE], [SINGLE_CHOICE], [MULTI_CHOICE] 중 하나여야 합니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
