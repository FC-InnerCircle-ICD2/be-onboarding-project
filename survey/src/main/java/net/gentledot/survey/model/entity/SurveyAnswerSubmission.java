package net.gentledot.survey.model.entity;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.gentledot.survey.model.entity.common.AnswerConverter;

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

    @ManyToOne
    @JoinColumn(name = "survey_question_id")
    private SurveyQuestion surveyQuestion;

    @ManyToOne
    @JoinColumn(name = "survey_question_option_id")
    private SurveyQuestionOption surveyQuestionOption;

    @Lob
    @Convert(converter = AnswerConverter.class)
    private String answer;

    public static SurveyAnswerSubmission of(SurveyQuestion surveyQuestion, SurveyQuestionOption surveyQuestionOption, String answer) {
        return new SurveyAnswerSubmission(null, null, surveyQuestion, surveyQuestionOption, answer);
    }

}

