package com.onboarding.servey.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.onboarding.common.exception.BaseException;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("SINGLE_LIST")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerContentSingle extends AnswerContent {

	private String optionId;

	@Builder
	public AnswerContentSingle(String optionId) {
		this.optionId = optionId;
	}

	@Override
	public void validate(boolean required) {
		if (required && StringUtils.isBlank(optionId)) {
			throw new BaseException("응답이 입력되지 않았습니다.");
		}
		if (!NumberUtils.isCreatable(optionId)) {
			throw new BaseException("올바른 응답값이 아닙니다.");
		}
	}
}
