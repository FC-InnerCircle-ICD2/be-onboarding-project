package com.survey.www.surveys.domain;

import com.survey.www.commons.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Schema(description = "설문조사 항목의 리스트 관리를 위한 도메인 객체")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SurveyQuestionOptions extends BaseEntity {
    @Schema(title = "고유번호", description = "리스트 고유번호입니다")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(title = "내용", description = "리스트 내용입니다")
    @Column(nullable = false)
    private String content;

    @Schema(title = "삭제 여부", description = "삭제 여부입니다")
    @Column(nullable = false)
    private Boolean isDeleted = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private SurveyQuestions surveyQuestions;

    @Builder
    public SurveyQuestionOptions(Long id, String content, Boolean isDeleted, SurveyQuestions surveyQuestions) {
        this.id = id;
        this.content = content;
        this.isDeleted = isDeleted;
        this.surveyQuestions = surveyQuestions;
    }

    public void update(String content, Boolean isDeleted) {
        this.content = content;
        this.isDeleted = isDeleted;
    }
}
