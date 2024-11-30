package org.survey.db.surveyitem;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.survey.db.BaseEntity;
import org.survey.db.BaseStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "survey_item")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SurveyItemEntity extends BaseEntity{

    @Column(nullable = false)
    private Long surveyId;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemInputType inputType;

    @Column(nullable = false)
    private Boolean required;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BaseStatus status;

    private LocalDateTime registeredAt;

    private LocalDateTime modifiedAt;

    private LocalDateTime unregisteredAt;
}
