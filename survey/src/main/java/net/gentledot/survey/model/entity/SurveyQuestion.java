package net.gentledot.survey.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.gentledot.survey.dto.request.SurveyQuestionOptionRequest;
import net.gentledot.survey.dto.request.SurveyQuestionRequest;
import net.gentledot.survey.model.enums.ItemRequired;
import net.gentledot.survey.model.enums.SurveyItemType;

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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "surveyQuestion")
    private List<SurveyQuestionOption> options;

    public static SurveyQuestion of(String itemName, String itemDescription, SurveyItemType itemType, ItemRequired required, List<SurveyQuestionOption> options) {
        SurveyQuestion surveyQuestion = new SurveyQuestion(null, itemName, itemDescription, itemType, required, null, options);
        if (options != null) {
            options.forEach(option -> option.setSurveyQuestion(surveyQuestion));
        }
        return surveyQuestion;
    }

    public static SurveyQuestion from(SurveyQuestionRequest questionRequest) {
        List<SurveyQuestionOptionRequest> questionRequestOptions = questionRequest.getOptions();
        List<SurveyQuestionOption> options = null;
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
}
