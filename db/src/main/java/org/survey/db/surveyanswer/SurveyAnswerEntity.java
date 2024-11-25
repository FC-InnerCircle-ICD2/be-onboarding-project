package org.survey.db.surveyanswer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.survey.db.BaseEntity;

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

    private LocalDateTime registeredAt;

    private LocalDateTime modifiedAt;

    private LocalDateTime unregisteredAt;
}
