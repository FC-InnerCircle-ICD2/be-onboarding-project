package com.survey.api.custom;

import com.survey.api.constant.CommonConstant;
import com.survey.api.entity.SurveyEntity;
import com.survey.api.entity.SurveyItemEntity;
import com.survey.api.exception.SurveyApiException;
import com.survey.api.form.SurveyForm;
import com.survey.api.form.SurveyItemForm;
import com.survey.api.util.ConvertUtil;
import io.micrometer.core.instrument.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Theo
 * @since 2024/11/30
 */
public class SurveyResponseSaveContractValidator implements ConstraintValidator<SurveyValidationContract, SurveyForm> {
    @Override
    public boolean isValid(SurveyForm survey, ConstraintValidatorContext context) {
        if(survey.getItems() != null) {
            for (SurveyItemForm item : survey.getItems()) {
                if (StringUtils.isEmpty(item.getResponseType())) {
                    throw new SurveyApiException(CommonConstant.ERR_DB_DATA_ID_ERROR, "응답 타입이 누락되었습니다.");
                }
            }
        } else {
            return false;
        }
        return true;
    }
}
