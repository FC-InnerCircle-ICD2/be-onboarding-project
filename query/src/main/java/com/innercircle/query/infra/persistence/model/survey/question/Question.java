package com.innercircle.query.infra.persistence.model.survey.question;

import com.innercircle.common.domain.survey.question.QuestionType;
import com.innercircle.common.infra.persistence.converter.StringListConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {

	private static final int MAX_NAME_SIZE = 20;
	private static final int MAX_DESCRIPTION_SIZE = 30;
	private static final int MAX_OPTION_SIZE = 3;

	@Id
	private String id;
	private String surveyId;
	private String name;
	private String description;
	private boolean required;
	@Enumerated(EnumType.STRING)
	private QuestionType type;
	@Convert(converter = StringListConverter.class)
	private List<String> options;

	public Question(String id, String surveyId, String name, String description, boolean required, QuestionType type, List<String> options) {
		this.id = id;
		this.surveyId = surveyId;
		this.name = name;
		this.description = description;
		this.required = required;
		this.type = type;
		this.options = options;
	}
}
