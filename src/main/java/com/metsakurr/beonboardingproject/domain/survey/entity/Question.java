package com.metsakurr.beonboardingproject.domain.survey.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.metsakurr.beonboardingproject.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "question")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @JsonIgnore
    @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "survey_idx", referencedColumnName = "idx")
    private Survey survey;

    @Comment("항목 이름")
    @Column(nullable = false)
    private String name;

    @Comment("항목 설명")
    @Column(columnDefinition = "TEXT")
    private String description;

    @Comment("항목 입력 형태")
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @Comment("항목 필수 여부")
    @ColumnDefault("false")
    @Column(name = "required_yn", nullable = false)
    private boolean isRequired;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Option> options = new ArrayList<>();

    public void deleteFromSurvey() {
        this.survey = null;
    }

    @Builder
    public Question(
            Survey survey,
            String name,
            String description,
            QuestionType questionType,
            boolean isRequired,
            List<Option> options
    ) {
        this.survey = survey;
        this.name = name;
        this.description = description;
        this.questionType = questionType;
        this.isRequired = isRequired;
        if (options == null) {
            this.options = new ArrayList<>();
        }
    }
}

