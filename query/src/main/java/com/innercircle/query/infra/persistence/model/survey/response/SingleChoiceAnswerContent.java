package com.innercircle.query.infra.persistence.model.survey.response;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@DiscriminatorValue("SINGLE_CHOICE")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SingleChoiceAnswerContent extends AnswerContent {

	private String selectedOption;
}
