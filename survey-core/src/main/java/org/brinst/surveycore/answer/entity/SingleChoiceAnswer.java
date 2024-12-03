package org.brinst.surveycore.answer.entity;

import org.brinst.surveycommon.enums.OptionType;
import org.brinst.surveycore.survey.entity.SurveyQuestion;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class SingleChoiceAnswer extends AnswerParent{
	private String answerValue;

	public SingleChoiceAnswer(Answer answer, SurveyQuestion surveyQuestion, String answers) {
		super(answer, surveyQuestion);
		this.answerValue = answers;
	}

	@Override
	public String  getAnswerValue() {
		return answerValue;
	}

	@Override
	public OptionType getOptionType() {
		return OptionType.SINGLE_CHOICE;
	}
}
