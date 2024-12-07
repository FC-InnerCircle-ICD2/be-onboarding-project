package com.innercircle.command.domain.survey.response;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@DiscriminatorValue("SINGLE_CHOICE")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SingleChoiceAnswerContent extends AnswerContent {

	private String selectedOption;

	SingleChoiceAnswerContent(String selectedOption) {
		super();
		this.selectedOption = selectedOption;
	}

	public static SingleChoiceAnswerContent of(String selectedOption) {
		return new SingleChoiceAnswerContent(selectedOption);
	}

	@Override
	public void validate(boolean isRequired) {
		if (isRequired && StringUtils.isBlank(this.selectedOption)) {
			throw new IllegalArgumentException("Single choice answer must not be empty");
		}
	}
}
