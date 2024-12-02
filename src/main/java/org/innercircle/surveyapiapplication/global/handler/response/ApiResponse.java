package org.innercircle.surveyapiapplication.global.handler.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.innercircle.surveyapiapplication.global.exception.CustomResponseStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private int code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> onSuccess(T data) {
        CustomResponseStatus success = CustomResponseStatus.SUCCESS;
        return new ApiResponse<>(success.getCode(), success.getMessage(), data);
    }

    public static ApiResponse<Void> onError(ResponseStatus status) {
        return new ApiResponse<>(status.getCode(), status.getMessage(), null);
    }

}