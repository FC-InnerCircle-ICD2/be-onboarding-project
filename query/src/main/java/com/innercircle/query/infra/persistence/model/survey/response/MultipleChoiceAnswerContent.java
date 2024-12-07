package com.innercircle.query.infra.persistence.model.survey.response;

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

	private List<String> selectedOptions;
}
