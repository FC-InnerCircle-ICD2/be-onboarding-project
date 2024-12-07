package com.survey.www.accounts.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleType {
    ADMIN("관리자"),
    WRITER("작성자"),
    RESPONDER("응답자");

    final private String name;
}
