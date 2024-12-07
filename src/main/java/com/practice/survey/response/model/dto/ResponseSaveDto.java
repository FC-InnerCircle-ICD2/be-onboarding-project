package com.practice.survey.response.model.dto;

import com.practice.survey.response.model.entity.Response;
import com.practice.survey.surveyVersion.model.entity.SurveyVersion;
import com.practice.survey.surveymngt.model.entity.Survey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseSaveDto {

    private SurveyVersion version;

    private String respondentId;

    public Response toEntity() {
        return Response.builder()
                .version(version)
                .respondentId(respondentId)
                .build();
    }

}
