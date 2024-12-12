package com.onboarding.servey.domain;

import java.util.List;

import javax.persistence.Convert;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.CollectionUtils;

import com.onboarding.common.converter.StringListConverter;
import com.onboarding.common.exception.BaseException;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("MULTI_LIST")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerContentMulti extends AnswerContent {

	@Convert(converter = StringListConverter.class)
	private List<String> optionIds;

	@Builder
	public AnswerContentMulti(List<String> optionId) {
		this.optionIds = optionId;
	}

	@Override
	public void validate(boolean required) {
		if (required && CollectionUtils.isEmpty(optionIds)) {
			throw new BaseException("응답이 입력되지 않았습니다.");
		}
		for (String optionId : optionIds) {
			if (!NumberUtils.isCreatable(optionId)) {
				throw new BaseException("올바른 응답값이 아닙니다.");
			}
		}
	}
}
