package ic2.onboarding.survey.global;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_VALIDATED("입력값 검증 실패", HttpStatus.BAD_REQUEST),

    NOT_VALIDATED_INPUT_TYPE("항목 입력 형태 이상", HttpStatus.BAD_REQUEST),

    NOT_FOUND("대상을 찾을 수 없음", HttpStatus.NOT_FOUND),

    SERVER_ERROR("서버 에러", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    private final String message;

    private final HttpStatus httpStatus;

}
