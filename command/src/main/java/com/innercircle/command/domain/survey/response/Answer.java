package com.innercircle.command.domain.survey.response;

import com.innercircle.common.domain.survey.question.QuestionType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer {

	private static final int MAX_SHORT_TEXT_LENGTH = 20;
	private static final int MAX_LONG_TEXT_LENGTH = 50;
	private static final int MAX_SINGLE_CHOICE_OPTIONS = 1;
	private static final int MAX_MULTIPLE_CHOICE_OPTIONS = 3;

	@Id
	private String id;
	private String surveyResponseId;
	private String questionId;
	@Enumerated(EnumType.STRING)
	private QuestionType questionType;
	private boolean required;
	@ElementCollection
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

	public void validate() {
		switch (this.questionType) {
			case SHORT_TEXT -> validateTextQuestion(MAX_SHORT_TEXT_LENGTH, this.required, "Short text");
			case LONG_TEXT -> validateTextQuestion(MAX_LONG_TEXT_LENGTH, this.required, "Long text");
			case SINGLE_CHOICE -> validateChoiceQuestion(MAX_SINGLE_CHOICE_OPTIONS, this.required, "Single choice");
			case MULTIPLE_CHOICE -> validateChoiceQuestion(MAX_MULTIPLE_CHOICE_OPTIONS, this.required, "Multiple choice");
		}
	}

	private void validateTextQuestion(int maxLength, boolean required, String questionTypeName) {
		if (CollectionUtils.isNotEmpty(this.selectedOptions)) {
			throw new IllegalArgumentException("%s question cannot have options".formatted(questionTypeName));
		}
		if ((required && StringUtils.isBlank(this.text)) || StringUtils.length(this.text) > maxLength) {
			throw new IllegalArgumentException(
					"%s answer must not be empty and must not exceed %d characters".formatted(questionTypeName, maxLength));
		}
	}

	private void validateChoiceQuestion(int maxOptions, boolean required, String questionTypeName) {
		if (StringUtils.isNotBlank(this.text)) {
			throw new IllegalArgumentException("%s question cannot have text".formatted(questionTypeName));
		}
		if ((required && CollectionUtils.isEmpty(this.selectedOptions)) || CollectionUtils.size(this.selectedOptions) > maxOptions) {
			throw new IllegalArgumentException(
					"%s question must have between 1 and %d options selected".formatted(questionTypeName, maxOptions));
		}
	}
}
