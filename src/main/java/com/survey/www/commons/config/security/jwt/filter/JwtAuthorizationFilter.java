package com.survey.www.commons.config.security.jwt.filter;

import com.survey.www.commons.config.security.jwt.dto.JwtDto;
import com.survey.www.commons.config.security.jwt.dto.LoginUser;
import com.survey.www.commons.config.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final String secretKey;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, String secretKey) {
        super(authenticationManager);
        this.secretKey = secretKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (isHeaderVerify(request, response)) {
            String token = request.getHeader(JwtDto.HEADER).replace(JwtDto.TOKEN_PREFIX, "");
            LoginUser loginUser = new JwtService().verify(token, secretKey);
            if (loginUser.getAccount() != null) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }

    private boolean isHeaderVerify(HttpServletRequest request, HttpServletResponse response) {
        String header = request.getHeader(JwtDto.HEADER);
        if (header == null || !header.startsWith(JwtDto.TOKEN_PREFIX)) {
            return false;
        } else {
            return true;
        }
    }
}
