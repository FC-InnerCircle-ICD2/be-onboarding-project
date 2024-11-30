package com.innercicle.security;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = DefaultWithMockJwtAuthenticationSecurityContextFactory.class)
public @interface WithMockJwtAuthentication {

    long id() default 1L;

    String email() default "leesg107@naver.com";

    String userName() default "관리자";

    String password() default "qlalfqjsgh1!";

    boolean admin() default false;

}