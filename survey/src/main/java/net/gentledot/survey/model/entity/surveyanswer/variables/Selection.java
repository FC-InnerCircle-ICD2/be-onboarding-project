package net.gentledot.survey.model.entity.surveyanswer.variables;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.gentledot.survey.model.entity.surveybase.SurveyQuestionOption;

import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Selection {
    private String selectedOption;
    private List<String> options;

    public Selection(String selectedOption, List<String> options) {
        this.selectedOption = selectedOption;
        this.options = options;
    }

    public static Selection of(String selectedOption, List<SurveyQuestionOption> options) {
        return new Selection(selectedOption, options.stream().map(SurveyQuestionOption::getOptionText).collect(Collectors.toList()));
    }
}
