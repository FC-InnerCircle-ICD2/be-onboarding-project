package com.metsakurr.beonboardingproject.domain.submission.entity;

import com.metsakurr.beonboardingproject.common.entity.BaseEntity;
import com.metsakurr.beonboardingproject.domain.survey.entity.Survey;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "response")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Submission extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_idx", referencedColumnName = "idx", nullable = false)
    private Survey survey;

    @OneToMany(mappedBy = "submission", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Answer> answers = new ArrayList<>();

    public void addAnswer(Answer answer) {
        answers.add(answer);
        answer.setSubmission(this);
    }

    @Builder
    public Submission(Survey survey) {
        this.survey = survey;
    }
}
