package com.survey.api.response;

import com.survey.api.util.DateUtil;
import lombok.Data;

import java.io.Serializable;

@Data
public class SurveyBaseResponse implements Serializable {

    private static final long serialVersionUID = -4127357495164126975L;

    private int statusCode;
    private String statusMessage;
    private String responseTime;

    public SurveyBaseResponse(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.responseTime = DateUtil.getCurrentTime();
    }

}
