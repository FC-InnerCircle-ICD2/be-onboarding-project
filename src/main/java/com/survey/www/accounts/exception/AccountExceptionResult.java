package com.survey.www.accounts.exception;

import com.survey.www.commons.config.exception.CommonExceptionResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountExceptionResult implements CommonExceptionResult {
    NOT_FOUND_ACCOUNT("등록된 회원 데이터를 찾을 수 없습니다");

    private final String message;
}
