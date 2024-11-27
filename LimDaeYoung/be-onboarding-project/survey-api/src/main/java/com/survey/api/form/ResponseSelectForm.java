package com.survey.api.form;

import com.survey.api.base.SurveyBaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ResponseSelectForm extends SurveyBaseVo {

    private static final long serialVersionUID = 1258001554806991338L;
    private Long id;
    private String searchParam;
    private int pageNumber;

}


