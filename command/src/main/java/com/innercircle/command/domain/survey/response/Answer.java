package com.innercircle.command.domain.survey.response;

import com.innercircle.common.domain.survey.question.QuestionSnapshot;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer {

	@Id
	private String id;
	private String surveyResponseId;
	@Embedded
	private QuestionSnapshot questionSnapshot;
	@ManyToOne(cascade = CascadeType.ALL)
	private AnswerContent content;

	public Answer(String id, String surveyResponseId, QuestionSnapshot questionSnapshot, AnswerContent content) {
		this.id = id;
		this.surveyResponseId = surveyResponseId;
		this.questionSnapshot = questionSnapshot;
		this.content = content;
	}

	public void validate() {
		var isRequired = this.questionSnapshot.isQuestionRequired();
		this.content.validate(isRequired);
	}
}
