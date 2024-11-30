package com.innercircle.command.domain.survey.question;

import com.innercircle.common.domain.survey.question.QuestionSnapshot;
import com.innercircle.common.domain.survey.question.QuestionType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

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
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "question_options", joinColumns = @JoinColumn(name = "question_id"))
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

	public void validate() {
		if ((this.type == QuestionType.SHORT_TEXT || this.type == QuestionType.LONG_TEXT) && CollectionUtils.isNotEmpty(this.options)) {
			throw new IllegalArgumentException("Short text question cannot have options");
		}
		if (StringUtils.isBlank(this.name) || StringUtils.length(this.name) > MAX_NAME_SIZE) {
			throw new IllegalArgumentException("Question name must not be empty and must not exceed %d characters".formatted(MAX_NAME_SIZE));
		}
		if (StringUtils.isBlank(this.description) || StringUtils.length(this.description) > MAX_DESCRIPTION_SIZE) {
			throw new IllegalArgumentException("Question description must not be empty and must not exceed %d characters".formatted(MAX_DESCRIPTION_SIZE));
		}
		if (CollectionUtils.size(this.options) > MAX_OPTION_SIZE) {
			throw new IllegalArgumentException("Question options must not exceed %d".formatted(MAX_OPTION_SIZE));
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		var question = (Question) o;
		return Objects.equals(id, question.id) &&
				Objects.equals(surveyId, question.surveyId) &&
				Objects.equals(name, question.name) &&
				Objects.equals(description, question.description) &&
				Objects.equals(required, question.required) &&
				Objects.equals(type, question.type) &&
				CollectionUtils.isEqualCollection(options, question.options);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, type);
	}

	public QuestionSnapshot getSnapshot() {
		return new QuestionSnapshot(id, surveyId, name, description, required, type, options);
	}
}
