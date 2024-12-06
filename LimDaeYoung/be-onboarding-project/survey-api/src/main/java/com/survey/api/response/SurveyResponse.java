package com.survey.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SurveyResponse implements Serializable {
    private static final long serialVersionUID = 6424187623493959228L;
    private Long id;
    private String name;
    private String description;
    private String regDtm;

    private List<SurveyItemResponse> itemList;
}
