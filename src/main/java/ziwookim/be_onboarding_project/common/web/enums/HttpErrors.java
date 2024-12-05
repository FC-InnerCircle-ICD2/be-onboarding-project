package ziwookim.be_onboarding_project.common.web.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import ziwookim.be_onboarding_project.common.web.error.HttpError;

@Getter
@RequiredArgsConstructor
public enum HttpErrors implements HttpError {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "요청된 URL 을 찾을 수 없습니다."),
    RESEARCH_NOT_FOUND(HttpStatus.BAD_REQUEST, "설문 데이터를 찾을 수 없습니다."),
    EMPTY_TITLE(HttpStatus.BAD_REQUEST, "설문 제목을 입력해주세요."),
    EMPTY_DESCRIPTION(HttpStatus.BAD_REQUEST, "설문 설명을 입력해주세요."),
    INVALID_RESEARCH_ITEM_SIZE(HttpStatus.BAD_REQUEST, "설문 항목은 1개에서 10개 입력 가능합니다."),
    MISMATCH_RESEARCH_ITEM_CHOICE(HttpStatus.BAD_REQUEST, "항목 입력 형태와 선택 리스트 데이터가 매칭 되지 않습니다."),
    INVALID_RESEARCH_ITEM_TYPE(HttpStatus.BAD_REQUEST, "항목 입력 형태는 단답형(1), 장문형(2), 단일 선택 리스트(3), 다중 선택 리스트(4) 중에서만 선택이 가능합니다."),
    ITEM_TYPE_NAME_NOT_FOUND(HttpStatus.BAD_REQUEST, "항목 입력 형태 이름을 찾을 수 없습니다."),
    RESEARCH_ANSWER_NOT_FOUND(HttpStatus.BAD_REQUEST, "설문 응답 데이터를 찾을 수 없습니다."),
    IGNORED_REQUIRED_ITEM(HttpStatus.BAD_REQUEST, "필수 응답 항목의 답변이 존재하지 않습니다."),
    INVALID_SINGLE_SELECTION_ANSWER(HttpStatus.BAD_REQUEST, "단일 선택 리스트 항목의 응답 정보로 적절하지 않은 데이터가 포함 되어 있습니다."),
    INVALID_MULTIPLE_SELECTION_ANSWER(HttpStatus.BAD_REQUEST, "다중 선택 리스트 항목의 응답 정보로 적절하지 않은 데이터가 포함 되어 있습니다."),
    MISMATCH_RESEARCH_ITEM_ANSWER_SIZE(HttpStatus.BAD_REQUEST, "설문 항목과 응답 개수가 일치하지 않습니다."),
    INVALID_RESEARCH_ANSWER(HttpStatus.BAD_REQUEST, "응답 형식이 적절하지 않습니다."),
    MISMATCH_RESEARCH_ITEM_ANSWER(HttpStatus.BAD_REQUEST, "설문 항목 입력 형태와 응답 형식이 매칭 되지 않습니다."),
    INVALID_ANSWER_SINGLE_SELECTION_ITEM(HttpStatus.BAD_REQUEST, "단일 선택 항목은 한가지 값만 선택할 수 있습니다."),
    INVALID_ANSWER_DUPLICATED_ANSWER(HttpStatus.BAD_REQUEST, "다중 선택 항목에 중복된 선택 값이 포함 되어 있습니다."),
    INVALID_ANSWER_SIZE_MULTIPLE_SELECTION_ITEM(HttpStatus.BAD_REQUEST, "다중 선택 항목의 값의 길이가 부적절합니다."),
    INVALID_ANSWER_MULTIPLE_SELECTION_ITEM(HttpStatus.BAD_REQUEST, "다중 선택 항목의 값 중 부적절한 값이 포함 되어 있습니다.")
    ;
    private final HttpStatus status;
    private final String message;

    @Override
    public HttpStatus getStatus() {
        return null;
    }

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
