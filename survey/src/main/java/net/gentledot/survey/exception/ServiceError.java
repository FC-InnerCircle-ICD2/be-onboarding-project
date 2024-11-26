package net.gentledot.survey.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ServiceError {
    // 생성 관련 오류
    SURVEY_CREATION_INSUFFICIENT_QUESTIONS("서베이 생성에 필요한 질문이 유효하지 않습니다.");

    private final String message;

    ServiceError(String message) {
        this.message = message;
    }
}
