package com.innercircle.command.domain.survey;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Survey {

	@Id
	private SurveyId id;
	private String name;
	private String description;

	public Survey(SurveyId id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}
}
