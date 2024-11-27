package ic2.onboarding.survey.dto;

import ic2.onboarding.survey.global.BizConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

import java.util.List;

public record SurveyUpdateRequest(@Valid SurveyForm basic,

                                  @Valid @Size(
                                          min = BizConstants.MIN_ITEMS_SIZE,
                                          max = BizConstants.MAX_ITEMS_SIZE,
                                          message = "{items.size}"
                                  )
                                  List<SurveyFormItem> items) {


    public String newSurveyName() {

        return this.basic().name();
    }


    public String newSurveyDescription() {

        return this.basic().description();
    }

}
