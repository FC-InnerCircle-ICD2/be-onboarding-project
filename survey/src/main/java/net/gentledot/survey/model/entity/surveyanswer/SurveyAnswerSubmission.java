package net.gentledot.survey.model.entity.surveyanswer;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = "surveyAnswer")
@Entity
public class SurveyAnswerSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "survey_answer_id")
    private SurveyAnswer surveyAnswer;

    private SurveyQuestionSnapshot surveyQuestionSnapshot;

    @ElementCollection
    private List<SurveyQuestionOptionSnapshot> surveyQuestionOptionSnapshot;

    public static SurveyAnswerSubmission of(SurveyAnswer surveyAnswer, SurveyQuestionSnapshot surveyQuestion, List<SurveyQuestionOptionSnapshot> surveyQuestionOptions) {
        return new SurveyAnswerSubmission(null, surveyAnswer, surveyQuestion, surveyQuestionOptions);
    }

}

