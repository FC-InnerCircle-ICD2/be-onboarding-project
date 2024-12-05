package com.innercicle.adapter.out.persistence.v1;

import com.innercicle.domain.ReplySurvey;
import com.innercicle.entity.CreatedEntity;
import com.innercicle.generator.IdGenerator;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "reply_survey")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplySurveyEntity extends CreatedEntity {

    @Id
    @IdGenerator
    @Column(name = "reply_survey_id")
    private Long id;

    /**
     * 설문 제목
     */
    private String name;

    /**
     * 설문 설명
     */
    private String description;

    @OneToMany(mappedBy = "replySurvey", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final List<ReplySurveyItemEntity> items = new ArrayList<>();

    public static ReplySurveyEntity from(ReplySurvey replySurvey) {
        ReplySurveyEntity entity = new ReplySurveyEntity();
        entity.name = replySurvey.name();
        entity.description = replySurvey.description();
        entity.items.addAll(replySurvey.items().stream()
                                .map(item -> ReplySurveyItemEntity.from(item, entity)).toList());
        return entity;
    }

    public ReplySurvey mapToDomain() {

        return ReplySurvey.builder()
            .id(this.id)
            .name(this.name)
            .description(this.description)
            .items(this.items.stream()
                       .map(ReplySurveyItemEntity::mapToDomain)
                       .toList())
            .build();
    }

}
