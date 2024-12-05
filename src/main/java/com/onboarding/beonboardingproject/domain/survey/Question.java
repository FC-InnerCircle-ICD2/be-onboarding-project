package com.onboarding.beonboardingproject.domain.survey;

import com.onboarding.beonboardingproject.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "question")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="question_id")
    private Long id;

    @Column(nullable = false)
    private String questionName;

    @Column
    private String questionDesc;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionType questionType; // 단답형, 장문형, 단일 선택, 다중 선택

    @Column(nullable = false)
    private boolean isRequired; // 필수 여부

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    private Survey survey; // 설문조사와의 연관관계

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options = new ArrayList<>();

    public void addOption(Option option) {
        if (options == null) {
            options = new ArrayList<>();
        }
        options.add(option);
        option.setQuestion(this);
    }
}
