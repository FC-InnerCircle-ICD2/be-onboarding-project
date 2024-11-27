package com.innercircle.command.domain.survey.response;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
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
	@ElementCollection
	@CollectionTable(name = "answer_options", joinColumns = @JoinColumn(name = "answer_id"))
	private List<String> selectedOptions;
	private String text;

	public Answer(String id, String surveyResponseId, String questionId, List<String> selectedOptions, String text) {
		this.id = id;
		this.surveyResponseId = surveyResponseId;
		this.questionId = questionId;
		this.selectedOptions = selectedOptions;
		this.text = text;
	}
}
