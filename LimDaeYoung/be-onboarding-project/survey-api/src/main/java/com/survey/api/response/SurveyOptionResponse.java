package com.survey.api.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class SurveyOptionResponse implements Serializable {
    private static final long serialVersionUID = -5874217734907270000L;
    private Long id;
    private String optionName;
    private String useYn;

}
