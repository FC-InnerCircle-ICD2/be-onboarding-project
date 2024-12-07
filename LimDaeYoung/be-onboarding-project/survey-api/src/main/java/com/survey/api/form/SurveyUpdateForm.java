package com.survey.api.form;

import com.survey.api.base.SurveyBaseVo;
import com.survey.api.custom.SurveyValidationContract;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@SurveyValidationContract
public class SurveyUpdateForm extends SurveyBaseVo {
    private static final long serialVersionUID = 1399210097971891604L;
    private String name;
    private String description;
    private long id;
    List<SurveyUpdateItemForm> items;
}


