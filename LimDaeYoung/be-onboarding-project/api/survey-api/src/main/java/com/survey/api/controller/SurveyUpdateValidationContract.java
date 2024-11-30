package com.survey.api.controller;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author Theo
 * @since 2024/11/30
 */
@Target(value = {ElementType.PARAMETER})
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Constraint(validatedBy = [SurveyUpdateValidationContractValidator.class])
public @interface SurveyUpdateValidationContract {
}
