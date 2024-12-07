package com.survey.www.commons.config.security.jwt.dto;

public interface JwtDto {
    int EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER = "Authorization";
}
