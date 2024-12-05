package ic2.onboarding.survey.entity;

import ic2.onboarding.survey.dto.AnswerInfo;
import ic2.onboarding.survey.dto.SurveyInfo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        indexes = @Index(name = "SURVEY_SUBMISSION_IDX_1", columnList = "survey_uuid")
)
public class SurveySubmission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String surveyUuid;

    // 답변 시점의 설문 정보
    @JdbcTypeCode(SqlTypes.JSON)
    private SurveyInfo surveyData;

    // 답변 내용
    @JdbcTypeCode(SqlTypes.JSON)
    private List<AnswerInfo> answerData;

}
