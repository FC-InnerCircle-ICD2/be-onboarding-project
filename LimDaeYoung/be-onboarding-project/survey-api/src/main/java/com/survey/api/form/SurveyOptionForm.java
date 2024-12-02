package com.survey.api.form;

import com.survey.api.base.SurveyBaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SurveyOptionForm extends SurveyBaseVo {
    private static final long serialVersionUID = 8270558061845707208L;
    private Long id;
    private String optionName;
    private int optionOrder;
    private String actionType;
}