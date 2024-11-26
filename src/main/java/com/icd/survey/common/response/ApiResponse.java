package com.icd.survey.common.response;

import com.icd.survey.common.response.enums.ResponseType;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class ApiResponse<T> {

    private T result;

    @Builder.Default
    private String response = ResponseType.SUCCESS.getResponseMessage();

    public void changeResponse(ResponseType type) {
        this.response = type.getResponseMessage();
    }

    public ApiResponse(T t) {
        this.result = t;
    }
}
