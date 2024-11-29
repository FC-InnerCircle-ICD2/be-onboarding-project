package com.onboarding.servey.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.onboarding.common.exception.BaseException;
import com.onboarding.servey.dto.request.OptionRequest;

@Component
public class ServeyValidator {

	private final static String singleAndMultiTypeErrorMessage = "SINGLE_LIST(단일 선택 리스트) 또는 MULTI_LIST(다중 선택 리스트)인 경우 선택할 수 있는 후보를 포함하여야 합니다.";
	private final static String shortAndLongTypeErrorMessage = "SHORT_TYPE(단문형) 또는 LONG_TYPE(장문형)인 경우 선택할 수 있는 후보를 포함할 수 없습니다.";
	private final static String questionSizeErrorMeesage = "설문 받을 항목은 1개 이상이어야 합니다.";

	public void checkTypeAndOptions(String type) {
		if (Arrays.asList("SHORT_TYPE", "LONG_TYPE").contains(type)) {
			throw new BaseException(shortAndLongTypeErrorMessage);
		}
	}

	public void checkTypeAndOptions(String type, List<OptionRequest> options) {
		if (Arrays.asList("SINGLE_LIST", "MULTI_LIST").contains(type) && (options == null || options.size() < 1)) {
			throw new BaseException(singleAndMultiTypeErrorMessage);
		} else if (Arrays.asList("SHORT_TYPE", "LONG_TYPE").contains(type) && (options != null && options.size() > 0)) {
			throw new BaseException(shortAndLongTypeErrorMessage);
		}
	}

	public void checkTypeIsNumeric(Long questionId, String type, List<Long> optionIds, String answer) {
		try {
			if ("SINGLE_LIST".equals(type)) {
				if (!optionIds.contains(Long.parseLong(answer))) {
					throw new BaseException("question[" + questionId + "] : 선택 할 수 있는 후보 식별자가 존재하지 않습니다.");
				}
			} else if ("MULTI_LIST".equals(type)) {
				String[] numbers = answer.split("\\|");
				for (String number : numbers) {
					if (!optionIds.contains(Long.parseLong(number))) {
						throw new BaseException("question[" + questionId + "] : 선택 할 수 있는 후보 식별자가 존재하지 않습니다.");
					}
				}
			}
		} catch (NumberFormatException e) {
			throw new BaseException("question[" + questionId + "] : SINGLE_LIST(단일 선택 리스트) 또는 MULTI_LIST(다중 선택 리스트)인 경우 숫자를 입력해주세요.");
		}
	}

	public void checkIsRequired(Long questionId, boolean isRequired, String answer) {
		if (isRequired && (answer == null || answer.isEmpty())) {
			throw new BaseException("question[" + questionId + "] : 필수 입력 항목이 입력되지 않았습니다.");
		}
	}

	public void checkQuestionSize(int size) {
		if (size == 0) {
			throw new BaseException(questionSizeErrorMeesage);
		}
	}

	public void checkOptionSize(int size) {
		if (size == 0) {
			throw new BaseException(singleAndMultiTypeErrorMessage);
		}
	}
}
