package org.innercircle.surveyapiapplication.global.handler.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private int code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> onSuccess(ResponseStatus status, T data) {
        return new ApiResponse<>(status.getCode(), status.getMessage(), data);
    }

    public static ApiResponse<Void> onError(ResponseStatus status) {
        return new ApiResponse<>(status.getCode(), status.getMessage(), null);
    }

}