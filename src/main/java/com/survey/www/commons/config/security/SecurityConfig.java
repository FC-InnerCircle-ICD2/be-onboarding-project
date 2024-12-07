package com.survey.www.commons.config.security;

import com.survey.www.commons.config.security.jwt.filter.JwtAuthenticationFilter;
import com.survey.www.commons.config.security.jwt.filter.JwtAuthorizationFilter;
import com.survey.www.commons.config.security.util.CustomResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    @Value("${security.secret.key}")
    private String secretKey;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AesBytesEncryptor aesBytesEncryptor() {
        return new AesBytesEncryptor(secretKey, "70726574657374");
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, secretKey);
        JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter(authenticationManager, secretKey);

        http.headers((headerConfig) -> headerConfig.frameOptions((HeadersConfigurer.FrameOptionsConfig::sameOrigin)));
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(cors -> cors.configurationSource(configurationSource()));
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);

        http.addFilter(jwtAuthenticationFilter);
        http.addFilter(jwtAuthorizationFilter);

        // 인증 실패
        http.exceptionHandling(handling -> handling.authenticationEntryPoint((request, response, authException) -> {
            Map<String, String> errors = new HashMap<>();
            errors.put("request", "로그인이 필요합니다");
            CustomResponseUtil.sendResponse(response, HttpStatus.UNAUTHORIZED, errors);
        }));

//        http.authorizeHttpRequests(authz -> authz.requestMatchers("/api/v1/accounts/**").permitAll()
//                                                 .requestMatchers("/api/**").authenticated()
//                                                 .anyRequest().permitAll());

        return http.build();
    }

    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
