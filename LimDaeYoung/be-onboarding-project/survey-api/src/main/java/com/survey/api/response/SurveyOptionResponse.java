package com.survey.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SurveyOptionResponse implements Serializable {
    private static final long serialVersionUID = -5874217734907270000L;
    private Long id;
    private String optionName;

}
