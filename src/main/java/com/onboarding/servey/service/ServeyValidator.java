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
