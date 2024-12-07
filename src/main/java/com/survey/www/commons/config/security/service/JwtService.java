package com.survey.www.commons.config.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.survey.www.accounts.code.RoleType;
import com.survey.www.accounts.domain.Account;
import com.survey.www.commons.config.security.jwt.dto.JwtDto;
import com.survey.www.commons.config.security.jwt.dto.LoginUser;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    public String create(LoginUser loginUser, String secretKey) {
        String jwtToken = JWT.create()
                             .withSubject("jwt")
                             .withClaim("id", loginUser.getAccount().getId())
                             .withClaim("role", loginUser.getAccount().getRoleType() + "")
                             .withExpiresAt(new Date(System.currentTimeMillis() + JwtDto.EXPIRATION_TIME))
                             .sign(Algorithm.HMAC512(secretKey));
        return JwtDto.TOKEN_PREFIX + jwtToken;
    }

    public LoginUser verify(String token, String secretKey) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(secretKey))
                                   .build()
                                   .verify(token);
        Long id = decodedJWT.getClaim("id").asLong();
        String role = decodedJWT.getClaim("role").asString();
        Account account = Account.builder()
                                 .id(id)
                                 .roleType(RoleType.valueOf(role))
                                 .build();
        return new LoginUser(account);
    }
}
