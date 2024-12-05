package com.survey.api.form;

import com.survey.api.base.SurveyBaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class SurveyUpdateItemForm extends SurveyBaseVo {
    private static final long serialVersionUID = 8775477852665756876L;
    private Long id;
    private String itemName;
    private String description;
    private String itemType;
    private boolean required;
    private String actionType;

    private List<SurveyUpdateOptionForm> optionList;

}


