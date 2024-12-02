package com.innercicle.adapter.out.persistence.survey.entity;

import com.innercicle.domain.v1.Survey;
import com.innercicle.entity.UpdatedEntity;
import com.innercicle.generator.IdGenerator;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 설문 엔티티
 *
 * @author seunggu.lee
 */
@Getter
@Entity
@Table(name = "survey")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SurveyEntity extends UpdatedEntity {

    /**
     * 설문 식별자
     */
    @Id
    @IdGenerator
    @Column(name = "survey_id")
    private Long id;

    /**
     * 설문 제목
     */
    private String name;

    /**
     * 설문 설명
     */
    private String description;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SurveyItemEntity> items;

    public static SurveyEntity from(Survey survey) {
        SurveyEntity entity = new SurveyEntity();
        entity.name = survey.name();
        entity.description = survey.description();
        entity.items = survey.items().stream()
            .map(SurveyItemEntity::from)
            .toList();
        return entity;
    }

    public Survey mapToDomain() {
        return Survey.builder()
            .id(this.id)
            .name(this.name)
            .description(this.description)
            .items(this.items.stream()
                       .map(SurveyItemEntity::mapToDomain)
                       .toList())
            .build();
    }

}
