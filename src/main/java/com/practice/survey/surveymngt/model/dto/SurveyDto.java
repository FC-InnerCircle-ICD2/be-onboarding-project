package com.practice.survey.surveymngt.model.dto;

import com.practice.survey.surveymngt.model.entity.Survey;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurveyDto {

    private Long surveyId;

    private String name;

    private String description;

    public Survey toEntity(){
        return Survey.builder()
                .surveyId(this.surveyId)
                .name(this.name)
                .description(this.description)
                .build();
    }
}
