package com.innercircle.command.domain.survey.question;

import com.innercircle.command.domain.Identifier;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionId extends Identifier {

	public QuestionId(String id) {
		super(id);
	}
}
