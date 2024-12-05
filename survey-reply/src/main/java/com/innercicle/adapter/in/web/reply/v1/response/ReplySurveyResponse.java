package com.innercicle.adapter.in.web.reply.v1.response;

import com.innercicle.domain.ReplySurvey;
import lombok.Getter;

@Getter
public class ReplySurveyResponse {

    private Long id;
    private String name;
    private String description;

    public static ReplySurveyResponse from(ReplySurvey replySurvey) {
        return null;
    }

}
