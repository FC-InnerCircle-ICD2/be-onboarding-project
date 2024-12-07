package net.gentledot.survey.domain.surveybase;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import net.gentledot.survey.application.service.in.model.request.SurveyQuestionOptionRequest;
import net.gentledot.survey.application.service.in.model.request.SurveyQuestionRequest;
import net.gentledot.survey.domain.enums.ItemRequired;
import net.gentledot.survey.domain.enums.SurveyItemType;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = "survey")
@Entity
public class SurveyQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemName;
    private String itemDescription;
    @Enumerated(EnumType.STRING)
    private SurveyItemType itemType;
    @Enumerated(EnumType.STRING)
    private ItemRequired required;

    @Setter(value = AccessLevel.PROTECTED)
    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @ElementCollection
    @CollectionTable(name = "survey_question_option", joinColumns = @JoinColumn(name = "survey_question_id"))
    private List<SurveyQuestionOption> options;

    public static SurveyQuestion of(String itemName, String itemDescription, SurveyItemType itemType, ItemRequired required, List<SurveyQuestionOption> options) {
        return new SurveyQuestion(null, itemName, itemDescription, itemType, required, null, options);
    }

    public static SurveyQuestion from(SurveyQuestionRequest questionRequest) {
        List<SurveyQuestionOptionRequest> questionRequestOptions = questionRequest.getOptions();
        List<SurveyQuestionOption> options = Collections.emptyList();
        if (questionRequestOptions != null) {
            options = questionRequestOptions.stream()
                    .map(SurveyQuestionOption::from)
                    .collect(Collectors.toList());
        }

        return SurveyQuestion.of(
                questionRequest.getQuestion(),
                questionRequest.getDescription(),
                questionRequest.getType(),
                questionRequest.getRequired(),
                options
        );
    }

    public void updateFromRequest(SurveyQuestionRequest questionRequest) {
        this.itemName = questionRequest.getQuestion();
        this.itemDescription = questionRequest.getDescription();
        this.itemType = questionRequest.getType();
        this.required = questionRequest.getRequired();
        this.options = questionRequest.getOptions().stream()
                .map(SurveyQuestionOption::from)
                .collect(Collectors.toList());
    }
}
