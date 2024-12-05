package org.innercircle.surveyapiapplication.domain.surveyItem.domain;

import lombok.Getter;
import org.innercircle.surveyapiapplication.domain.answer.domain.Answer;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.type.SurveyItemType;
import org.innercircle.surveyapiapplication.global.exception.CustomException;
import org.innercircle.surveyapiapplication.global.exception.CustomResponseStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MultiChoiceSurveyItem extends SurveyItem {

    private List<String> options;
    private List<Answer> answers = new ArrayList<>();

    public MultiChoiceSurveyItem(Long id, int version, String name, String description, boolean required, Long surveyId, List<String> options) {
        super(id, version, name, description, required, surveyId);
        this.options = options;
    }

    public MultiChoiceSurveyItem(Long id, String name, String description, boolean required, List<String> options) {
        super(id, 1, name, description, required);
        this.options = options;
    }

    @Override
    public void answer(Answer answer) {
        if (!options.contains(answer.getResponse())) {
            throw new CustomException(CustomResponseStatus.NOT_FOUND_QUESTION_OPTION);
        }
        this.answers.add(answer);
        answer.setQuestionId(this.getId());
    }

    @Override
    public SurveyItemType getType() {
        return SurveyItemType.MULTI_CHOICE_ANSWER;
    }

}
