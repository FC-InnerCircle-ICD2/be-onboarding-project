package com.survey.api.response;

import com.survey.api.entity.SurveyEntity;
import com.survey.api.entity.SurveyResponseEntity;
import com.survey.api.entity.SurveyResponseItemEntity;
import com.survey.api.entity.SurveyResponseOptionEntity;
import com.survey.api.util.DateUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class SurveyResponse implements Serializable {
    private static final long serialVersionUID = 6424187623493959228L;
    private Long id;
    private String name;
    private String description;
    private String regDtm;
    private String useYn;

    private List<SurveyItemResponse> itemList;
}
