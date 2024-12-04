package com.onboarding.beonboardingproject.domain.survey;

import com.onboarding.beonboardingproject.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "survey")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Survey extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="survey_id")
    private Long id;

    @Column(nullable = false)
    private String surveyName;

    private String surveyDesc;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    @Builder
    public Survey(String surveyName, String surveyDesc, List<Question> questions) {
        this.surveyName = surveyName;
        this.surveyDesc = surveyDesc;
        this.questions = questions;
    }

    public void addQuestion(Question question) {
        if (questions == null) {
            questions = new ArrayList<>();
        }
        questions.add(question);
        question.setSurvey(this);
    }

    public void removeQuestion(Question question) {
        if (questions == null) {
            questions = new ArrayList<>();
        }
        questions.remove(question);
        question.setSurvey(null);  // 질문과 설문 관계 끊기
    }
}
