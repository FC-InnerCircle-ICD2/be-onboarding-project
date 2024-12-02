package com.practice.survey.surveyVersion.model.dto;

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
public class SurveyVersionSaveRequestDto {

    private int versionNumber;

    public SurveyVersion toEntity(Survey survey, int versionNumber) {
        return SurveyVersion.builder()
            .versionNumber(versionNumber)
            .survey(survey)
            .build();
    }
}
