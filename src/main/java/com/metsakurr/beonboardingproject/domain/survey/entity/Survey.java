package com.metsakurr.beonboardingproject.domain.survey.entity;

import com.metsakurr.beonboardingproject.common.entity.BaseEntity;
import com.metsakurr.beonboardingproject.domain.survey.dto.SurveyRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.*;

@Entity(name = "survey")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Survey extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Comment("설문조사 이름")
    @Column(nullable = false)
    private String name;

    @Comment("설문조사 설명")
    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Question> questions = new ArrayList<>();

    public static Survey create(SurveyRequest request) {
        Survey survey = Survey.builder()
                .name(request.getName())
                .description(request.getDescription())
                .questions(new ArrayList<>())
                .build();
        request.getQuestions().forEach(questionRequest -> {
            Question question = Question.create(questionRequest);
            survey.addQuestion(question);
        });
        return survey;
    }

    public void update(String name, String description) {
        this.name = name;
        this.description = name;
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
        question.setSurvey(this);
    }

    public void deleteQuestion(Question question) {
        this.questions.remove(question);
        question.setSurvey(null);
    }

    @Builder
    private Survey(
            String name,
            String description,
            List<Question> questions
    ) {
        this.name = name;
        this.description = description;
        this.questions = questions;
    }
}