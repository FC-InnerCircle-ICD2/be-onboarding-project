package org.survey.domain.service.surveyitem;

import jakarta.persistence.*;
import lombok.*;
import org.survey.domain.service.BaseStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "survey_item")
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@IdClass(SurveyItemPK.class)
@Builder
public class SurveyItemEntity{

    @Id
    private Long id;

    @Id
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
