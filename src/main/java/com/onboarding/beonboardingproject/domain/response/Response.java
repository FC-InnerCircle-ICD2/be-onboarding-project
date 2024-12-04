package com.onboarding.beonboardingproject.domain.response;
import com.onboarding.beonboardingproject.common.entity.BaseEntity;
import com.onboarding.beonboardingproject.domain.survey.Survey;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "response")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Response extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id", nullable = false)
    private Survey survey; // 어떤 설문조사와 연결되어 있는지

    @OneToMany(mappedBy = "response", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers = new ArrayList<>();

    public void addAnswer(Answer answer) {
        if (answers == null) {
            answers = new ArrayList<>();
        }
        answers.add(answer);
        answer.setResponse(this);
    }
}
