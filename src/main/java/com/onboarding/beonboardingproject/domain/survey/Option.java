package com.onboarding.beonboardingproject.domain.survey;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "QuestionOption")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="option_id")
    private Long id;

    @Column(nullable = false)
    private String optionValue; // 옵션 값

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question; // 연결된 질문

}
