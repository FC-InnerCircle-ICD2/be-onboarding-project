package org.brinst.surveycore.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class AnswerItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private List<String> answerValue;
	@ManyToOne
	private Answer answer;
	@ManyToOne
	private SurveyQuestion surveyQuestion;

	public AnswerItem(List<String> answerValue, Answer answer, SurveyQuestion surveyQuestion) {
		this.answerValue = answerValue;
		this.answer = answer;
		this.surveyQuestion = surveyQuestion;
	}
}
