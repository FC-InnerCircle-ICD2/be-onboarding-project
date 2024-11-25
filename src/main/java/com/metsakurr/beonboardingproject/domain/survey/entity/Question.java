package com.metsakurr.beonboardingproject.domain.survey.entity;

import com.metsakurr.beonboardingproject.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

@Entity(name = "question")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_idx", referencedColumnName = "idx", nullable = false)
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
}
