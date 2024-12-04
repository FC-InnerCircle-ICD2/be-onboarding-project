package com.survey.api.custom;

import com.survey.api.constant.CommonConstant;
import com.survey.api.form.SurveyForm;
import com.survey.api.form.SurveyItemForm;
import com.survey.api.form.SurveyOptionForm;
import io.micrometer.core.instrument.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Theo
 * @since 2024/11/30
 */
public class SurveyContractValidator implements ConstraintValidator<SurveyValidationContract, SurveyForm> {
    @Override
    public boolean isValid(SurveyForm survey, ConstraintValidatorContext context) {
        //필수값 체크
        if (StringUtils.isBlank(survey.getName())) {
            return false;
        }

        if (StringUtils.isBlank(survey.getDescription())) {
            return false;
        }

        if (survey.getItems() != null) {
            for (SurveyItemForm item : survey.getItems()) {
                if (StringUtils.isBlank(item.getItemName())) {
                    return false;
                }

                if (StringUtils.isBlank(item.getDescription())) {
                    return false;
                }

                if (StringUtils.isBlank(item.getItemType())) {
                    return false;
                }

                if (!(CommonConstant.SINGLE_ITEM.equals(item.getItemType())
                        || CommonConstant.MULTI_ITEM.equals(item.getItemType())
                        || CommonConstant.SHORT_ITEM.equals(item.getItemType())
                        || CommonConstant.LONG_ITEM.equals(item.getItemType()))) {
                    return false;
                }

                if (item.getOptionList() != null) {
                    for (SurveyOptionForm option : item.getOptionList()) {
                        if (StringUtils.isBlank(option.getOptionName())) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
