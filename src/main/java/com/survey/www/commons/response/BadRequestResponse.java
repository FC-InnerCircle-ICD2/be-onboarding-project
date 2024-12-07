package com.survey.www.commons.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Schema(description = "유효성 오류 응답 DTO")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BadRequestResponse<T> {
    @Schema(title = "응답 코드", description = "응답 코드입니다", example = "400")
    private final Integer code = HttpStatus.BAD_REQUEST.value();

    @Schema(title = "응답 메세지", description = "응답 메세지입니다", example = "BAD REQUEST")
    private final String message = HttpStatus.BAD_REQUEST.name();

    @Schema(description = "응답 데이터입니다")
    private T data;
}
