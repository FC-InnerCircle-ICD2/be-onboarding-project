package com.survey.api.custom;

import com.survey.api.constant.CommonConstant;
import com.survey.api.form.*;
import io.micrometer.core.instrument.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Theo
 * @since 2024/11/30
 */
public class SurveyResponseContractValidator implements ConstraintValidator<SurveyValidationContract, SurveyResponseForm> {

    @Override
    public boolean isValid(SurveyResponseForm survey, ConstraintValidatorContext context) {
        if (survey.getItems() != null) {
            for (SurveyResponseItemForm item : survey.getItems()) {
                if (StringUtils.isBlank(item.getItemType())) {
                    return false;
                }

                if (!(CommonConstant.SINGLE_ITEM.equals(item.getItemType())
                        || CommonConstant.MULTI_ITEM.equals(item.getItemType())
                        || CommonConstant.SHORT_ITEM.equals(item.getItemType())
                        || CommonConstant.LONG_ITEM.equals(item.getItemType()))) {
                    return false;
                }
            }
        }
        return true;
    }
}
