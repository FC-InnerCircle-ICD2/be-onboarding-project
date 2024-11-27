package com.onboarding.beonboardingproject.domain.survey;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "option")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long optionId;

    @Column(nullable = false)
    private String optionValue; // 옵션 값

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question; // 연결된 질문
}
