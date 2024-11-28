package ic2.onboarding.survey.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        indexes = {
                @Index(name = "SURVEY_SUBMISSION_ITEM_IDX_1", columnList = "SURVEY_ID, NAME")
        }
)
public class SurveySubmissionItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "SURVEY_ID",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Survey survey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "SURVEY_ITEM_ID",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private SurveyItem surveyItem;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String answer;

}
