package com.icd.survey.common.response;

import com.icd.survey.common.response.enums.ResponseType;
import lombok.Builder;

@Builder
public class ApiResponse<T> {

    private T result;

    @Builder.Default
    private String response = ResponseType.SUCCESS.getResponseMessage();

    public static ApiResponse successResponse() {
        return ApiResponse.builder().build();
    }

    public void changeResponse(ResponseType type) {
        this.response = type.getResponseMessage();
    }

    public ApiResponse(T t) {
        this.result = t;
    }
}
