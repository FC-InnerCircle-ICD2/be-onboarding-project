package com.innercircle.query.infra.persistence.model.survey.response;

import com.innercircle.common.infra.persistence.converter.StringListConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@DiscriminatorValue("MULTIPLE_CHOICE")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MultipleChoiceAnswerContent extends AnswerContent {

	@Convert(converter = StringListConverter.class)
	private List<String> selectedOptions;

	public MultipleChoiceAnswerContent(List<String> selectedOptions) {
		this.selectedOptions = selectedOptions;
	}
}
