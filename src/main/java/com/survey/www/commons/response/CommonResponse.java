package com.survey.www.commons.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Schema(description = "공통 응답 DTO")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommonResponse<T> {
    @Schema(title = "응답 코드", description = "응답 코드입니다")
    private Integer code;

    @Schema(title = "응답 메세지", description = "응답 메세지입니다")
    private String message;

    @Schema(description = "응답 데이터입니다")
    private T data;

    public CommonResponse(T data) {
        this.code = HttpStatus.OK.value();
        this.message = HttpStatus.OK.name();
        this.data = data;
    }

    public CommonResponse(HttpStatus httpStatus, String msg, T data) {
        this.code = httpStatus.value();
        this.message = msg;
        this.data = data;
    }
}
