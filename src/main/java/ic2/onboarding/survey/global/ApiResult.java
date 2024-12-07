package ic2.onboarding.survey.global;

import lombok.Getter;

import java.util.Map;

@Getter
public class ApiResult<T> {

    private final String code;
    private final String message;
    private final Map<String, String> validations;
    private final T result;

    public ApiResult(ErrorCode errorCode) {
        this.code = errorCode.name();
        this.message = errorCode.getMessage();
        this.validations = null;
        this.result = null;
    }

    public ApiResult(T result) {
        this.code = "SUCCESS";
        this.message = null;
        this.validations = null;
        this.result = result;
    }

    public ApiResult(ErrorCode errorCode, Map<String, String> validations) {
        this.code = errorCode.name();
        this.message = errorCode.getMessage();
        this.validations = validations;
        this.result = null;
    }

}
