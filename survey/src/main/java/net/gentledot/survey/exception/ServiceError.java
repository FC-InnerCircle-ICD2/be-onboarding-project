package net.gentledot.survey.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum ServiceError {
    // 생성 관련 오류 (100)
    CREATION_INVALID_REQUEST("1001", "서베이 생성 요청에 오류가 있습니다."),
    CREATION_INSUFFICIENT_QUESTIONS("1002", "서베이 생성에 필요한 질문은 최소 1개 이상, 10개 이하까지 생성 가능합니다."),
    CREATION_INSUFFICIENT_OPTIONS("1003", "서베이 생성에 필요한 질문 옵션이 유효하지 않습니다."),
    CREATION_DUPLICATE_QUESTIONS("1004", "서베이 생성/수정 요청 시 중복된 질문이 설정될 수 없습니다."),

    // 조회 관련 오류 (200)
    INQUIRY_SURVEY_NOT_FOUND("2001", "요청한 서베이를 찾을 수 없습니다."),

    // 응답 관련 오류 (300)
    SUBMIT_INVALID_QUESTION_ID("3001", "제출한 응답의 질문이 확인되지 않습니다."),
    SUBMIT_INVALID_QUESTION_OPTION_ID("3002", "제출한 응답의 질문의 옵션이 확인되지 않습니다."),
    SUBMIT_UNSUPPORTED_ATTRIBUTE("3003", "서비스에서 지원되지 않는 응답 형식입니다."),
    SUBMIT_DATA_CONVERT_ERROR("3004", "입력된 데이터의 변환 처리에 실패하였습니다."),

    // 공통 오류 (900)
    BAD_REQUEST("9400", "요청이 유효하지 않습니다."),
    INTERNAL_SERVER_ERROR("9500", "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");


    private final String code;
    private final String message;
}
