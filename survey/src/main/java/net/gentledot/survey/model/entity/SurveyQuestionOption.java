package net.gentledot.survey.model.entity;

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
import net.gentledot.survey.dto.request.SurveyQuestionOptionRequest;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = "surveyQuestion")
@Entity
public class SurveyQuestionOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String optionText;

    @Setter(value = AccessLevel.PROTECTED)
    @ManyToOne
    @JoinColumn(name = "survey_question_id")
    private SurveyQuestion surveyQuestion;

    public static SurveyQuestionOption of(String optionText) {
        return new SurveyQuestionOption(null, optionText, null);
    }

    public static SurveyQuestionOption from(SurveyQuestionOptionRequest surveyQuestionOptionRequest) {
        return SurveyQuestionOption.of(surveyQuestionOptionRequest.getOptionText());
    }
}