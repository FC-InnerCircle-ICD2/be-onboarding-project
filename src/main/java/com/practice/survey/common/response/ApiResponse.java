package com.practice.survey.common.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ApiResponse<T> {
    @JsonProperty("common")
    private Common common;

    @JsonProperty("data")
    private Value<T> data;

    @JsonIgnore
    public ApiResponse<T> responseOk() {
        return ApiResponse.<T>builder()
                .common(Common.builder().build().responseOk())
                .build();
    }

    @JsonIgnore
    public ApiResponse<T> responseOk(T value) {
        return ApiResponse.<T>builder()
                .data(Value.<T>builder().build().responseOk(value))
                .common(Common.builder().build().responseOk())
                .build();
    }

    @JsonIgnore
    public ApiResponse<T> serverError(StatusEnum status) {
        return ApiResponse.<T>builder()
                .common(Common.builder().build().serverError(status))
                .build();
    }

    @JsonIgnore
    public ApiResponse<T> serverError(int code, String message) {
        return ApiResponse.<T>builder()
                .common(Common.builder().build().serverError(code, message))
                .build();
    }
}
