package com.metsakurr.beonboardingproject.domain.survey.entity;

import com.metsakurr.beonboardingproject.common.entity.BaseEntity;
import com.metsakurr.beonboardingproject.domain.survey.dto.QuestionRequest;
import com.metsakurr.beonboardingproject.domain.survey.dto.SurveyRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.*;
import java.util.stream.Collectors;

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

    public void addQuestion(Question question) {
        this.questions.add(question);
        question.setSurvey(this);
    }

    public void deleteQuestion(Question question) {
        this.questions.remove(question); // TODO: 목록에서 삭제하면 question을 Survey랑 같이 업데이트 할 수 없어 리스트에는 남겨 놓고 Survey랑만 연결 끊어 보는
        question.setSurvey(null);
    }

    public void update(SurveyRequest request) {
        // TODO: 이름과 설명이 변경된 경우 새로운 설문으로 볼 수 있지 않을까?
        this.name = request.getName();
        this.description = request.getDescription();

        List<Question> deletedQuestion = questions.stream()
                .filter(
                        questionRequest -> request.getQuestions().stream()
                                .noneMatch(question -> Objects.equals(question.getIdx(), questionRequest.getIdx()))
                )
                .toList();
        deletedQuestion.forEach(this::deleteQuestion);

        Map<Long, QuestionRequest> newQuestionMap = request.getQuestions().stream()
                        .collect(Collectors.toMap(QuestionRequest::getIdx, question -> question));
        List<Question> newQuestions = new ArrayList<>();
        List<Question> oldQuestions = new ArrayList<>();

        questions.forEach(question -> {
            Optional.ofNullable(newQuestionMap.get(question.getIdx()))
                    .ifPresent(questionRequest -> {
                        Question newQuestion = questionRequest.toEntity();
                        if (!Objects.equals(newQuestion, question)) {
                            oldQuestions.add(question);
                            newQuestions.add(newQuestion);
                        }
                    });
        });
        newQuestions.forEach(this::addQuestion);
        oldQuestions.forEach(this::deleteQuestion);

        request.getQuestions().stream()
                .filter(question -> question.getIdx() == null)
                .map(QuestionRequest::toEntity)
                .forEach(this::addQuestion);

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