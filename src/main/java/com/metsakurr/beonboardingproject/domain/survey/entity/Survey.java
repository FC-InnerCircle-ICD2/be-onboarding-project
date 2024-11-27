package com.metsakurr.beonboardingproject.domain.survey.entity;

import com.metsakurr.beonboardingproject.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();

    public void addQuestion(Question question) {
        question.setSurvey(this);
        this.questions.add(question);
    }

    @Builder
    public Survey(
            String name,
            String description
    ) {
        this.name = name;
        this.description = description;
    }
}