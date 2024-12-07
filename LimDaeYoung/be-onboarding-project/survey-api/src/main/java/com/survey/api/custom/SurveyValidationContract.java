package com.survey.api.custom;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @author Theo
 * @since 2024/11/30
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {SurveyContractValidator.class, SurveyUpdateContractValidator.class, SurveyResponseContractValidator.class})
public @interface SurveyValidationContract {
    String message() default "can't contain special character";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}