package org.survey.domain.service.surveyanswer;

import jakarta.persistence.*;
import lombok.*;
import org.survey.domain.service.BaseStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "survey_reply")
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(SurveyReplyPK.class)
public class SurveyReplyEntity{

    @Id
    private Long id;

    @Id
    private Long surveyId;

    @Id
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
