package com.icd.survey.api.entity.survey;

import com.icd.survey.api.dto.survey.request.SurveyRequest;
import com.icd.survey.api.dto.survey.request.SurveyUpdateRequest;
import com.icd.survey.api.entity.base.BaseEntity;
import com.icd.survey.api.entity.dto.SurveyDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "survey")
@ToString
public class Survey extends BaseEntity {
    @Id
    @Column(name = "survey_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long surveySeq;

    @Column(name = "survey_name", length = 255, nullable = false)
    private String surveyName;

    @Column(name = "survey_description", length = 1000, nullable = false)
    private String surveyDescription;

    @Column(name = "ip_address", length = 255, nullable = false)
    private String ipAddress;

    public SurveyDto of() {
        return SurveyDto
                .builder()
                .surveySeq(this.surveySeq)
                .surveyName(this.surveyName)
                .surveyDescription(this.surveyDescription)
                .ipAddress(this.ipAddress)
                .build();
    }

    public void update(SurveyUpdateRequest request) {
        if (StringUtils.hasText(request.getSurveyName())) {
            this.surveyName = request.getSurveyName();
        }
        if (StringUtils.hasText(request.getSurveyDescription())) {
            this.surveyDescription = request.getSurveyDescription();
        }
    }
}
