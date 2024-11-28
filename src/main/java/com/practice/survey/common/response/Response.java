package com.practice.survey.common.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@Accessors(chain = true)
public class Response<T> {
    @JsonProperty("common")
    private Common common;

    @JsonProperty("data")
    private Value<T> data;

    @JsonIgnore
    public Response<T> responseOk() {
        return Response.<T>builder()
                .common(Common.builder().build().responseOk())
                .build();
    }


    @JsonIgnore
    public Response<T> responseOk(T value) {
        return Response.<T>builder()
                .common(Common.builder().build().responseOk())
                .data(Value.<T>builder().build().responseOk(value))
                .build();
    }
}
