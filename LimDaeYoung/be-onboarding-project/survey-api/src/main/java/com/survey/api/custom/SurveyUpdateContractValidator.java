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
public class SurveyUpdateContractValidator implements ConstraintValidator<SurveyValidationContract, SurveyUpdateForm> {

    @Override
    public boolean isValid(SurveyUpdateForm survey, ConstraintValidatorContext context) {

        //필수값 체크
        if (StringUtils.isBlank(survey.getName())) {
            return false;
        }

        if (StringUtils.isBlank(survey.getDescription())) {
            return false;
        }

        if (survey.getItems() != null) {
            for (SurveyUpdateItemForm item : survey.getItems()) {
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

                if (StringUtils.isBlank(item.getActionType())) {
                    return false;
                }

                if (!(CommonConstant.ACTION_TYPE_UPDATE.equals(item.getActionType())
                        || CommonConstant.ACTION_TYPE_CREATE.equals(item.getActionType())
                        || CommonConstant.ACTION_TYPE_DELETE.equals(item.getActionType()))) {
                    return false;
                }

                if (item.getOptionList() != null) {
                    for (SurveyUpdateOptionForm option : item.getOptionList()) {
                        if (StringUtils.isBlank(option.getOptionName())) {
                            return false;
                        }

                        if (StringUtils.isBlank(option.getActionType())) {
                            return false;
                        }

                        if (!(CommonConstant.ACTION_TYPE_UPDATE.equals(option.getActionType())
                                || CommonConstant.ACTION_TYPE_CREATE.equals(option.getActionType())
                                || CommonConstant.ACTION_TYPE_DELETE.equals(option.getActionType()))) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
