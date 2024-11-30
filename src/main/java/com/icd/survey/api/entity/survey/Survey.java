package com.icd.survey.api.entity.survey;

import com.icd.survey.api.entity.base.BaseEntity;
import com.icd.survey.api.entity.survey.dto.SurveyDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import java.util.List;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
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
    private String ipAddress = "127.0.0.1";

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_seq")
    private List<SurveyItem> surveyItemList;

    public static Survey createSurveyRequest(SurveyDto dto) {
        Survey survey = new Survey();
        survey.surveyName = dto.getSurveyName();
        survey.surveyDescription = dto.getSurveyDescription();
        survey.ipAddress = dto.getIpAddress();
        return survey;
    }

    public SurveyDto of() {
        return SurveyDto
                .builder()
                .surveySeq(this.surveySeq)
                .surveyName(this.surveyName)
                .surveyDescription(this.surveyDescription)
                .ipAddress(this.ipAddress)
                .build();
    }

    public void update(SurveyDto dto) {
        if (StringUtils.hasText(dto.getSurveyName())) {
            this.surveyName = dto.getSurveyName();
        }
        if (StringUtils.hasText(dto.getSurveyDescription())) {
            this.surveyDescription = dto.getSurveyDescription();
        }
    }
}
