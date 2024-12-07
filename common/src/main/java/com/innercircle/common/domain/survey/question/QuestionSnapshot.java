package com.innercircle.common.domain.survey.question;

import com.innercircle.common.infra.persistence.converter.StringListConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class QuestionSnapshot {

	private String questionId;
	private String questionSurveyId;
	private String questionName;
	private String questionDescription;
	private boolean questionRequired;
	@Enumerated(EnumType.STRING)
	private QuestionType questionType;
	@Convert(converter = StringListConverter.class)
	private List<String> questionOptions;

	public QuestionSnapshot(String questionId, String questionSurveyId, String questionName, String questionDescription,
			boolean questionRequired, QuestionType questionType, List<String> questionOptions) {
		this.questionId = questionId;
		this.questionSurveyId = questionSurveyId;
		this.questionName = questionName;
		this.questionDescription = questionDescription;
		this.questionRequired = questionRequired;
		this.questionType = questionType;
		this.questionOptions = questionOptions;
	}
}
