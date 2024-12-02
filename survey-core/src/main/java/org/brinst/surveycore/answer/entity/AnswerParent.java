package org.brinst.surveycore.answer.entity;

import org.brinst.surveycommon.enums.OptionType;
import org.brinst.surveycore.survey.entity.SurveyQuestion;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "answer_type")
@Getter
@NoArgsConstructor
public abstract class AnswerParent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Answer answer;
	@ManyToOne
	private SurveyQuestion surveyQuestion;

	public AnswerParent(Answer answer, SurveyQuestion surveyQuestion) {
		this.answer = answer;
		this.surveyQuestion = surveyQuestion;
	}

	public abstract Object getAnswerValue();
	public abstract OptionType getOptionType();
}
