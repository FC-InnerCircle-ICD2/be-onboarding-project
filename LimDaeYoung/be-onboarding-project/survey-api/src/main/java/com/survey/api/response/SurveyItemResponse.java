package com.survey.api.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SurveyItemResponse implements Serializable {
    private static final long serialVersionUID = -5874217734907270000L;
    private Long id;
    private String itemName;
    private String description;
    private String answer;
    private String itemType;
    private boolean required;
    private String regDtm;
    private String useYn;

    List<SurveyOptionResponse> selectOptions;
}
