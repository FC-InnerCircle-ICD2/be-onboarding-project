package org.innercircle.surveyapiapplication.global.handler.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.innercircle.surveyapiapplication.global.exception.CustomResponseStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomApiResponse<T> {

    private int code;
    private String message;
    private T data;

    public static <T> CustomApiResponse<T> onSuccess() {
        CustomResponseStatus success = CustomResponseStatus.SUCCESS;
        return new CustomApiResponse<>(success.getCode(), success.getMessage(), null);
    }

    public static <T> CustomApiResponse<T> onSuccess(T data) {
        CustomResponseStatus success = CustomResponseStatus.SUCCESS;
        return new CustomApiResponse<>(success.getCode(), success.getMessage(), data);
    }

    public static CustomApiResponse<Void> onError(ResponseStatus status) {
        return new CustomApiResponse<>(status.getCode(), status.getMessage(), null);
    }

}