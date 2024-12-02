package com.survey.api.form;

import com.survey.api.base.SurveyBaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class SurveyItemForm extends SurveyBaseVo {
    private static final long serialVersionUID = -1909026336916149499L;
    private Long id;
    private String itemName;
    private String description;
    private String itemType;
    private boolean required;
    private String actionType;
    private String[] answer;
    private String responseType;

    private List<SurveyOptionForm> optionList;

}


