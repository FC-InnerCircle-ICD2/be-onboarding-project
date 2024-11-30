package org.survey.db.surveyanswer;

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
@Table(name = "survey_answer")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SurveyAnswerEntity extends BaseEntity{

    @Column(nullable = false)
    private Long surveyId;

    @Column(nullable = false)
    private Long itemId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BaseStatus status;

    private LocalDateTime registeredAt;

    private LocalDateTime modifiedAt;

    private LocalDateTime unregisteredAt;
}
