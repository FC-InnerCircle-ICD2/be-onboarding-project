package com.survey.api.form;

import com.survey.api.base.SurveyBaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class SurveyUpdateForm extends SurveyBaseVo {
    private static final long serialVersionUID = 7603717296695242967L;
    private long id;
    private String name;
    private String description;
    List<SurveyItemUpdateForm> items;
}


