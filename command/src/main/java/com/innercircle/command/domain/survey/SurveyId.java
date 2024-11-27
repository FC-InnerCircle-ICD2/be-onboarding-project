package com.innercircle.command.domain.survey;

import com.innercircle.command.domain.Identifier;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SurveyId extends Identifier {

	public SurveyId(String id) {
		super(id);
	}
}
