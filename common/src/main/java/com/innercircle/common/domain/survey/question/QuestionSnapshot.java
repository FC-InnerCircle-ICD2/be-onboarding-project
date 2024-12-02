package com.innercircle.common.domain.survey.question;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionSnapshot {

	private String id;
	private String surveyId;
	private String name;
	private String description;
	private boolean required;
	private QuestionType type;
	private List<String> options;

	public QuestionSnapshot(String id, String surveyId, String name, String description, boolean required, QuestionType type, List<String> options) {
		this.id = id;
		this.surveyId = surveyId;
		this.name = name;
		this.description = description;
		this.required = required;
		this.type = type;
		this.options = options;
	}
}
