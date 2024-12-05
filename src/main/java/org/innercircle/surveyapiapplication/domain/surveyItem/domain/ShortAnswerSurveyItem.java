package org.innercircle.surveyapiapplication.domain.surveyItem.domain;

import lombok.Getter;
import org.innercircle.surveyapiapplication.domain.answer.domain.Answer;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.type.SurveyItemType;

@Getter
public class ShortAnswerSurveyItem extends SurveyItem {

    private Answer answer;

    public ShortAnswerSurveyItem(Long id, int version, String name, String description, boolean required, Long surveyId) {
        super(id, version, name, description, required, surveyId);
    }

    public ShortAnswerSurveyItem(Long id, String name, String description, boolean required) {
        super(id, 1, name, description, required);
    }

    @Override
    public void answer(Answer answer) {
        this.answer = answer;
        answer.setQuestionId(this.getId());
    }

    @Override
    public SurveyItemType getType() {
        return SurveyItemType.SHORT_ANSWER;
    }

}
