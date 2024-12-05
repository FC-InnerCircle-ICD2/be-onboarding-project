package ic2.onboarding.survey.entity;

import ic2.onboarding.survey.dto.AnswerInfo;
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
@Table(
        indexes = @Index(name = "ANSWER_SUBMISSION_HISTORY_IDX_1", columnList = "survey_uuid, question_name")
)
public class AnswerHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private Long submissionId;

    private String surveyUuid;

    private String questionName;

    private String textAnswer;

    // 답변 시점의 설문 정보
    @JdbcTypeCode(SqlTypes.JSON)
    private SurveyInfo.Question questionData;

    // 답변 내용
    @JdbcTypeCode(SqlTypes.JSON)
    private AnswerInfo answerInfo;

}
