package com.onboarding.beonboardingproject.domain.survey;

import com.onboarding.beonboardingproject.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "survey")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Survey extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long surveyId;

    @Column(nullable = false)
    private String surveyName;

    private String surveyDesc;

    @OneToMany(mappedBy = "survey")
    private List<Question> questionList = new ArrayList<>();

    public void addQuestion(Question question) {
        questionList.add(question);
        question.setSurvey(this);
    }
}
