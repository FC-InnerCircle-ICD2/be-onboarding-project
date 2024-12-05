package ic2.onboarding.survey.global;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_VALIDATED("입력값 검증 실패", HttpStatus.BAD_REQUEST),
    CHOICE_TYPE_NEEDS_OPTIONS("선택 리스트가 비어있습니다.", HttpStatus.BAD_REQUEST),
    DUPLICATED_ANSWER("중복 된 답변이 존재합니다.", HttpStatus.BAD_REQUEST),
    REQUIRED_ANSWER_OMISSION("필수 답변이 누락되었습니다.", HttpStatus.BAD_REQUEST),
    NOT_VALIDATED_ANSWER_LENGTH(
            String.format("답변 길이는 [%s] ~ [%s] 사이입니다.", BizConstants.MIN_TEXT_ANSWER_LENGTH, BizConstants.MAX_TEXT_ANSWER_LENGTH),
            HttpStatus.BAD_REQUEST),
    NOT_VALIDATED_CHOICE_OPTION("올바르지 않은 선택입니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND("대상을 찾을 수 없음", HttpStatus.NOT_FOUND),

    SERVER_ERROR("서버 에러", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    private final String message;

    private final HttpStatus httpStatus;

}
