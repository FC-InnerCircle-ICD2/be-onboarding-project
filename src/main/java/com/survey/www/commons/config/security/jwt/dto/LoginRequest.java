package com.survey.www.commons.config.security.jwt.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.beans.factory.annotation.Configurable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Configurable
public class LoginRequest {
    @JsonIgnoreProperties
    @NotBlank(message = "아이디를 입력해 주세요")
    private String userId;

    @JsonIgnoreProperties
    @NotBlank(message = "비밀번호를 입력해 주세요")
    private String userPw;
}
