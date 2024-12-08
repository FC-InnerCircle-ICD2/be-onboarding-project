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
public class ResponseTemplate<T> {
    @JsonProperty("common")
    private Common common;

    @JsonProperty("data")
    private Value<T> data;

    @JsonIgnore
    public ResponseTemplate<T> responseOk() {
        return ResponseTemplate.<T>builder()
                .common(Common.builder().build().responseOk())
                .build();
    }

    @JsonIgnore
    public ResponseTemplate<T> responseOk(T value) {
        return ResponseTemplate.<T>builder()
                .data(Value.<T>builder().build().responseOk(value))
                .common(Common.builder().build().responseOk())
                .build();
    }

    @JsonIgnore
    public ResponseTemplate<T> serverError(StatusEnum status) {
        return ResponseTemplate.<T>builder()
                .common(Common.builder().build().serverError(status))
                .build();
    }

    @JsonIgnore
    public ResponseTemplate<T> serverError(int code, String message) {
        return ResponseTemplate.<T>builder()
                .common(Common.builder().build().serverError(code, message))
                .build();
    }
}
