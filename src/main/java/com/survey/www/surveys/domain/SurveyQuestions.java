package com.survey.www.surveys.domain;

import com.survey.www.commons.domain.BaseEntity;
import com.survey.www.surveys.code.SurveyQuestionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Schema(description = "설문조사 항목 관리를 위한 도메인 객체")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SurveyQuestions extends BaseEntity {
    @Schema(title = "고유번호", description = "항목 고유번호입니다")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(title = "항목명", description = "항목 이름입니다")
    @Column(nullable = false)
    private String questionNm;

    @Schema(title = "설명", description = "항목 설명입니다")
    @Column(length = 1000)
    private String description;

    @Schema(title = "입력 형태", description = "항목 입력 형태입니다")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SurveyQuestionType surveyQuestionType;

    @Schema(title = "필수 여부", description = "항목 필수 여부입니다")
    @Column(nullable = false)
    private Boolean isRequired = Boolean.FALSE;

    @Schema(title = "삭제 여부", description = "삭제 여부입니다")
    @Column(nullable = false)
    private Boolean isDeleted = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @Builder
    public SurveyQuestions(Long id, String questionNm, String description, SurveyQuestionType surveyQuestionType, Boolean isRequired, Boolean isDeleted, Survey survey) {
        this.id = id;
        this.questionNm = questionNm;
        this.description = description;
        this.surveyQuestionType = surveyQuestionType;
        this.isRequired = isRequired;
        this.isDeleted = isDeleted;
        this.survey = survey;
    }

    public void update(String questionNm, String description, SurveyQuestionType surveyQuestionType, Boolean isRequired, Boolean isDeleted) {
        this.questionNm = questionNm;
        this.description = description;
        this.surveyQuestionType = surveyQuestionType;
        this.isRequired = isRequired;
        this.isDeleted = isDeleted;
    }
}
