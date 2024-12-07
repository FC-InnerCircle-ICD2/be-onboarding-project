package org.brinst.surveycommon.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	BAD_REQUEST("올바르지 않은 값입니다.", 400),
	CHECK_SURVEY_ITEM_COUNT("설문 받을 항목은 1개 ~ 10개를 입력해주세요", 400),
	NOT_FOUND("해당 값을 찾을 수 없습니다. 값을 확인하고 재시도 해주세요.", 404),
	CONFLICT("중복된 값이 존재합니다.", 409),
	INTERNAL_SERVER_ERROR("서버 내부에 에러가 발생하였습니다. ", 500);

	private final String name;
	private final int code;
}
