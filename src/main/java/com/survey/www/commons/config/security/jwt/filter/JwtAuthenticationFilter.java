package com.survey.www.commons.config.security.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.survey.www.commons.config.security.jwt.dto.JwtDto;
import com.survey.www.commons.config.security.jwt.dto.LoginRequest;
import com.survey.www.commons.config.security.jwt.dto.LoginResponse;
import com.survey.www.commons.config.security.jwt.dto.LoginUser;
import com.survey.www.commons.config.security.service.JwtService;
import com.survey.www.commons.config.security.util.CustomResponseUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final String secretKey;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, String secretKey) {
        super(authenticationManager);
        setFilterProcessesUrl("/api/login");
        this.authenticationManager = authenticationManager;
        this.secretKey = secretKey;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            ObjectMapper om = new ObjectMapper();
            LoginRequest loginRequest = om.readValue(request.getInputStream(), com.survey.www.commons.config.security.jwt.dto.LoginRequest.class);

            Map<String, String> errors =  new HashMap<>();
            Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
            Set<ConstraintViolation<LoginRequest>> validate = validator.validate(loginRequest);
            for (ConstraintViolation<LoginRequest> next : validate) {
                errors.put(next.getPropertyPath().toString(), next.getMessage());
            }

            if (!errors.isEmpty()) {
                throw new InternalAuthenticationServiceException(om.writeValueAsString(errors));
            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUserId(), loginRequest.getUserPw());

            return authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage());
        }
    }

    // 로그인 실패
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "아이디 또는 비밀번호가 일치하지 않습니다");
        CustomResponseUtil.sendResponse(response, HttpStatus.BAD_REQUEST, errors);
    }

    // 로그인 성공
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        LoginUser loginUser = (LoginUser) authResult.getPrincipal();
        String accessToken = new JwtService().create(loginUser, secretKey)
                                             .replace(JwtDto.TOKEN_PREFIX, "");
        CustomResponseUtil.sendResponse(response, HttpStatus.OK, new LoginResponse(accessToken));
    }
}
