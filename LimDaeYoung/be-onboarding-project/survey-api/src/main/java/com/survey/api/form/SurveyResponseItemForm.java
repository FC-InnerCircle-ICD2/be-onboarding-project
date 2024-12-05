package com.survey.api.form;

import com.survey.api.base.SurveyBaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class SurveyResponseItemForm extends SurveyBaseVo {

    private static final long serialVersionUID = -5193725520597074523L;
    private Long id;

    private String itemType;
    private String actionType;
    private String longAnswer;
    private String shortAnswer;

    private String[] selectOptions;
}


