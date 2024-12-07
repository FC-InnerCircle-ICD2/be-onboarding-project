package com.innercircle.command.domain.survey.response;

import com.innercircle.common.infra.persistence.converter.StringListConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

@DiscriminatorValue("MULTIPLE_CHOICE")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MultipleChoiceAnswerContent extends AnswerContent {

	private static final int MAX_MULTIPLE_CHOICE_OPTIONS = 3;

	@Convert(converter = StringListConverter.class)
	private List<String> selectedOptions;

	MultipleChoiceAnswerContent(List<String> selectedOptions) {
		super();
		this.selectedOptions = selectedOptions;
	}

	public static MultipleChoiceAnswerContent of(List<String> selectedOptions) {
		return new MultipleChoiceAnswerContent(selectedOptions);
	}

	@Override
	public void validate(boolean isRequired) {
		if ((isRequired && CollectionUtils.isEmpty(this.selectedOptions)) || CollectionUtils.size(this.selectedOptions) > MAX_MULTIPLE_CHOICE_OPTIONS) {
			throw new IllegalArgumentException("Multiple choice question must have between 1 and %d options selected".formatted(MAX_MULTIPLE_CHOICE_OPTIONS));
		}
	}
}
