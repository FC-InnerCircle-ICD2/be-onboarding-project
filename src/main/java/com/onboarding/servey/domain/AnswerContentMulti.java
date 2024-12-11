package com.onboarding.servey.domain;

import java.util.List;

import javax.persistence.Convert;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.onboarding.common.converter.LongListConverter;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("MULTI_LIST")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerContentMulti extends AnswerContent {

	@Convert(converter = LongListConverter.class)
	private List<String> optionId;

	@Builder
	public AnswerContentMulti(List<String> optionId) {
		this.optionId = optionId;
	}
}
