package com.survey.api.form;

import com.survey.api.base.SurveyBaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class SurveyResponseForm extends SurveyBaseVo {
    private static final long serialVersionUID = 3989307078717498383L;

    private Long id;
    List<SurveyResponseItemForm> items;
}


