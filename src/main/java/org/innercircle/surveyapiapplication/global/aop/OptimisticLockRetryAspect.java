package org.innercircle.surveyapiapplication.global.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class OptimisticLockRetryAspect {

    @Around("@annotation(retryOnOptimisticLock)")
    public Object retryOnOptimisticLock(ProceedingJoinPoint joinPoint, RetryOnOptimisticLock retryOnOptimisticLock) throws Throwable {
        int maxRetries = retryOnOptimisticLock.maxRetries();
        int attempt = 0;

        while (true) {
            try {
                return joinPoint.proceed(); // 메서드 실행
            } catch (OptimisticLockingFailureException e) {
                attempt++;
                log.warn("Optimistic Lock 발생. Retry attempt {}/{}, 대상메서드: {}", attempt, maxRetries, joinPoint.getSignature());

                if (attempt > maxRetries) {
                    log.error("Optimistic Lock 재시도 가능 횟수 초과, 대상 메서드: {}", joinPoint.getSignature());
                    throw e;
                }

                Thread.sleep(100);
            }
        }
    }
}
