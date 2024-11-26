package com.survey.api.form;

import com.survey.api.base.SurveyBaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class SurveyItemForm extends SurveyBaseVo {
    private static final long serialVersionUID = -1909026336916149499L;
    private String itemName;
    private String description;
    private String itemType;
    private boolean required;

    private List<SurveyOptionForm> optionList;

}


