package ic2.onboarding.survey.entity;

import ic2.onboarding.survey.dto.SurveyInfo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Survey extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    @Setter
    @JdbcTypeCode(SqlTypes.JSON)
    private SurveyInfo surveyInfo;

}
