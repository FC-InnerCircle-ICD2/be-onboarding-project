package com.metsakurr.beonboardingproject.domain.survey.entity;

import com.metsakurr.beonboardingproject.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity(name = "survey")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Survey extends BaseEntity {
    @Comment("설문조사 이름")
    @Column(nullable = false)
    private String name;

    @Comment("설문조사 설명")
    @Column(columnDefinition = "TEXT")
    private String description;
}