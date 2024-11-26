package com.survey.api.response;


import com.survey.api.util.DateUtil;
import lombok.Data;

import java.io.Serializable;

@Data
public class SurveyErrorResult implements Serializable {
	private static final long serialVersionUID = 7554240140488926371L;

	private int statusCode;
	private String statusMessage;
	private String responseTime;

	public SurveyErrorResult(int statusCode, String statusMessage) {
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
		this.responseTime = DateUtil.getCurrentTime();
	}
}
