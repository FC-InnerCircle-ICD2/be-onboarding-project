package net.gentledot.survey.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ServiceError {
    // 생성 관련 오류
    CREATION_INVALID_REQUEST("서베이 생성 요청에 오류가 있습니다."),
    CREATION_INSUFFICIENT_QUESTIONS("서베이 생성에 필요한 질문은 최소 1개 이상, 10개 이하까지 생성 가능합니다."),
    CREATION_INSUFFICIENT_OPTIONS("서베이 생성에 필요한 질문 옵션이 유효하지 않습니다.");

    private final String message;

    ServiceError(String message) {
        this.message = message;
    }
}
