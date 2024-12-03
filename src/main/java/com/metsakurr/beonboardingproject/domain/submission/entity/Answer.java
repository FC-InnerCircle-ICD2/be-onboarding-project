package com.metsakurr.beonboardingproject.domain.submission.entity;

import com.metsakurr.beonboardingproject.domain.survey.entity.Question;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.Column;
import lombok.*;

@Entity(name = "answer")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_idx", referencedColumnName = "idx", nullable = false)
    private Question question;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "response_idx", referencedColumnName = "idx", nullable = false)
    private Submission submission;

    @Column(name = "answer_text", columnDefinition = "TEXT")
    private String answerText;

    @Builder
    public Answer(
            Question question,
            String answerText
    ) {
        this.question = question;
        this.answerText = answerText;
    }

}
