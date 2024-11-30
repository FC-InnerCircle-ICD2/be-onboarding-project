package com.survey.api.controller;

import com.survey.api.constant.CommonConstant;
import com.survey.api.entity.SurveyEntity;
import com.survey.api.entity.SurveyItemEntity;
import com.survey.api.exception.SurveyApiException;
import com.survey.api.form.SurveyItemUpdateForm;
import com.survey.api.form.SurveyOptionUpdateForm;
import com.survey.api.form.SurveyUpdateForm;
import io.micrometer.core.instrument.util.StringUtils;

/**
 * @author Theo
 * @since 2024/11/30
 */
public class SurveyUpdateValidationContractValidator implements ConstraintValidator<SurveyUpdateValidationContract, SurveyUpdateForm> {
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
            for (SurveyItemUpdateForm item : survey.getItems()) {
                if (StringUtils.isBlank(item.getItemName())) {
                    throw new SurveyApiException(CommonConstant.ERR_DATA_NOT_FOUND, CommonConstant.ERR_MSG_DATA_NOT_FOUND + "item Name");
                }

                if (StringUtils.isBlank(item.getDescription())) {
                    throw new SurveyApiException(CommonConstant.ERR_DATA_NOT_FOUND, CommonConstant.ERR_MSG_DATA_NOT_FOUND + "item description");
                }

                if (StringUtils.isBlank(item.getItemType())) {
                    throw new SurveyApiException(CommonConstant.ERR_DATA_NOT_FOUND, CommonConstant.ERR_MSG_DATA_NOT_FOUND + "item type");
                }

                if (!(CommonConstant.SINGLE_ITEM.equals(item.getItemType())
                        || CommonConstant.MULTI_ITEM.equals(item.getItemType())
                        || CommonConstant.SHORT_ITEM.equals(item.getItemType())
                        || CommonConstant.LONG_ITEM.equals(item.getItemType()))) {
                    throw new SurveyApiException(CommonConstant.ERR_FAIL, CommonConstant.ERROR_TYPE_REQUEST_INVALID + " item type");
                }

            }
        }
        return false;
    }
}
