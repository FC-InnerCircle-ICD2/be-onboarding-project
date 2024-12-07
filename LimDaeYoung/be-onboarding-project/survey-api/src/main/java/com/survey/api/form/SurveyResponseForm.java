package com.survey.api.form;

import com.survey.api.base.SurveyBaseVo;
import com.survey.api.custom.SurveyValidationContract;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@SurveyValidationContract
public class SurveyResponseForm extends SurveyBaseVo {
    private static final long serialVersionUID = 1797524719462112684L;
    private long id;
    List<SurveyResponseItemForm> items;
}


