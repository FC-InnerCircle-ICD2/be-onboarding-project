package com.metsakurr.beonboardingproject.domain.survey.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "option")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @JsonIgnore
    @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "question_idx", referencedColumnName = "idx")
    private Question question;

    @Column(nullable = false)
    private String name;

    @Builder
    public Option(
            Question question,
            String name
    ) {
        this.question = question;
        this.name = name;
    }
}
