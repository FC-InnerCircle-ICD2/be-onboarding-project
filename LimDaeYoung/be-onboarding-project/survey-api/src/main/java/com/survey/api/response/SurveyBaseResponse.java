package com.survey.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.survey.api.util.DateUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SurveyBaseResponse<T> implements Serializable {

    private static final long serialVersionUID = -4127357495164126975L;

    private int statusCode;
    private String statusMessage;
    private String responseTime;
    private List<T> data;

    public SurveyBaseResponse(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.responseTime = DateUtil.getCurrentTime();
    }

    public SurveyBaseResponse(int statusCode, String statusMessage, List<T> data) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.responseTime = DateUtil.getCurrentTime();
        this.data = data;
    }
}
