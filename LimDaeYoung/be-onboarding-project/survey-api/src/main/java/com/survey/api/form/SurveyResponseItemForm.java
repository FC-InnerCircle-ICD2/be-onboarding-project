package com.survey.api.form;

import com.survey.api.base.SurveyBaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SurveyResponseItemForm extends SurveyBaseVo {
    private static final long serialVersionUID = -5631076297444960936L;
    private Long id;
    private String reponseType;
    private String[] answer;
}


