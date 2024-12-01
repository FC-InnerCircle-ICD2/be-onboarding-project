package com.practice.survey.surveyVersion.model.dto;

import com.practice.survey.surveymngt.model.entity.Survey;
import com.practice.survey.surveyVersion.model.entity.SurveyVersion;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurveyVersionDto {

    private Long versionId;

//    private SurveyDto survey;
    private Long surveyId; // Survey 객체를 직접 포함하는 대신 ID만 포함
    // SurveyDto를 포함할 경우 DTO가 너무 복잡해지거나 순환 참조 문제를 일으킬 수 있으므로, 이를 surveyId 필드로 대체하는 방식을 권장합니다. 이 방식은 DTO가 가볍고 사용하기 쉬워집니다.

    private int versionNumber;

    public SurveyVersion toEntity(Survey survey, int versionNumber) {
        return SurveyVersion.builder()
                .versionId(this.versionId)
                .versionNumber(this.versionNumber)
                .survey(survey)  // Survey는 필요한 경우 직접 설정해 줍니다.
                .build();
    }
}
