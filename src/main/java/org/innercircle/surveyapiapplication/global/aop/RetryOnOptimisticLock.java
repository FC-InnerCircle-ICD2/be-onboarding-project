package org.innercircle.surveyapiapplication.global.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RetryOnOptimisticLock {
    int maxRetries() default 3; // 최대 재시도 횟수
}