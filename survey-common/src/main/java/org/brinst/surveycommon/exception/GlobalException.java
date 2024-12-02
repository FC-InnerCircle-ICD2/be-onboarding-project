package org.brinst.surveycommon.exception;

import org.brinst.surveycommon.enums.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GlobalException extends RuntimeException {
	private final ErrorCode errorCode;

	public GlobalException(ErrorCode errorCode, String detailMessage) {
		super(detailMessage); // 사용자 지정 메시지 사용
		this.errorCode = errorCode;
	}
}
