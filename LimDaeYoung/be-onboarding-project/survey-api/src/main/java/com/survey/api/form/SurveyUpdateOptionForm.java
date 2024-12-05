package com.survey.api.form;

import com.survey.api.base.SurveyBaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SurveyUpdateOptionForm extends SurveyBaseVo {
    private static final long serialVersionUID = 3280552355153677600L;
    private Long id;
    private String optionName;
    private int optionOrder;
    private String actionType;
}