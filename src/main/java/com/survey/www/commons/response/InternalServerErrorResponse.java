package com.survey.www.commons.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Schema(description = "서버 오류 응답 DTO")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class InternalServerErrorResponse<T> {
    @Schema(title = "응답 코드", description = "응답 코드입니다", example = "500")
    private final Integer code = HttpStatus.INTERNAL_SERVER_ERROR.value();

    @Schema(title = "응답 메세지", description = "응답 메세지입니다", example = "INTERNAL SERVER ERROR")
    private final String message = HttpStatus.INTERNAL_SERVER_ERROR.name();

    @Schema(description = "응답 데이터입니다")
    private T data;
}
