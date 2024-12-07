package com.survey.www.accounts.exception;

import com.survey.www.commons.config.exception.CommonExceptionResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccountException extends RuntimeException {
    private final AccountExceptionResult accountExceptionResult;
}
