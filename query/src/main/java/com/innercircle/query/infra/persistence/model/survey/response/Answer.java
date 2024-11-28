package com.innercircle.query.infra.persistence.model.survey.response;

import com.innercircle.query.infra.persistence.model.survey.question.QuestionType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import java.util.List;
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
	private String questionId;
	@Enumerated(EnumType.STRING)
	private QuestionType questionType;
	private boolean required;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "answer_options", joinColumns = @JoinColumn(name = "answer_id"))
	private List<String> selectedOptions;
	private String text;

	public Answer(String id, String surveyResponseId, String questionId, QuestionType questionType, boolean required, List<String> selectedOptions,
			String text) {
		this.id = id;
		this.surveyResponseId = surveyResponseId;
		this.questionId = questionId;
		this.questionType = questionType;
		this.required = required;
		this.selectedOptions = selectedOptions;
		this.text = text;
	}
}
