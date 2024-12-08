package com.innercircle.surveryproject.modules.dto.validators;

import com.innercircle.surveryproject.modules.dto.SurveyCreateDto;
import com.innercircle.surveryproject.modules.dto.SurveyItemDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

/**
 * 설믄조사 생성 validator
 */
@Component
public class SurveyCreateDtoValidator implements Validator {

    @Override public boolean supports(Class<?> clazz) {
        return SurveyCreateDto.class.equals(clazz);
    }

    @Override public void validate(Object target, Errors errors) {
        SurveyCreateDto surveyCreateDto = (SurveyCreateDto)target;

        List<SurveyItemDto> surveyItemDtoList = surveyCreateDto.getSurveyItemDtoList();
        if (surveyItemDtoList.isEmpty() || surveyItemDtoList.size() > 10) {
            errors.rejectValue("surveyItemDtoList", "field.invalid", "설문조사 항목은 1~10개 까지 입력 가능합니다.");
        }
    }

}
