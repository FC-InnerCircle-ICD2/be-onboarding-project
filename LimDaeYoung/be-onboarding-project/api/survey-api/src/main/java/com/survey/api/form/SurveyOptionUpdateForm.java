package com.survey.api.form;

import com.survey.api.base.SurveyBaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SurveyOptionUpdateForm extends SurveyBaseVo {
    private static final long serialVersionUID = 4700047973473106572L;
    private Long id;
    private String actionType;
    private String optionName;
    private int optionOrder;
}