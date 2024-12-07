package com.survey.www.surveys.domain;

import com.survey.www.commons.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "설문조사 답변 내용 관리를 위한 도메인 객체")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SurveyAnswerQuestions extends BaseEntity {
    @Schema(title = "고유번호", description = "설문조사 답변 내용 고유번호입니다")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(title = "내용", description = "답변 내용입니다")
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    private SurveyAnswers surveyAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private SurveyQuestions surveyQuestions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_option_id")
    private SurveyQuestionOptions surveyQuestionOptions;

    @Builder
    public SurveyAnswerQuestions(Long id, String content, SurveyAnswers surveyAnswer, SurveyQuestions surveyQuestions, SurveyQuestionOptions surveyQuestionOptions) {
        this.id = id;
        this.content = content;
        this.surveyAnswer = surveyAnswer;
        this.surveyQuestions = surveyQuestions;
        this.surveyQuestionOptions = surveyQuestionOptions;
    }
}
