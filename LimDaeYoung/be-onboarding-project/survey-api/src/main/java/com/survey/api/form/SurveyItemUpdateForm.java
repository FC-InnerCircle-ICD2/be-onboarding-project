package com.survey.api.form;

import com.survey.api.base.SurveyBaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class SurveyItemUpdateForm extends SurveyBaseVo {
    private static final long serialVersionUID = 8533437301718416189L;
    private Long id;
    private String itemName;
    private String description;
    private String itemType;
    private String actionType;

    private boolean required;

    private List<SurveyOptionUpdateForm> optionList;

}


