package com.onboarding.servey.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.commons.lang3.StringUtils;

import com.onboarding.common.exception.BaseException;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("SHORT_TYPE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerContentShort extends AnswerContent {

	private String text;

	@Builder
	public AnswerContentShort(String text) {
		this.text = text;
	}

	@Override
	public void validate(boolean required) {
		if (required && StringUtils.isBlank(text)) {
			throw new BaseException("응답이 입력되지 않았습니다.");
		}
	}
}
