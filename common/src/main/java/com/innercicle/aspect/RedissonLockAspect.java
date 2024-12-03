package com.innercicle.aspect;

import com.innercicle.advice.exceptions.AlreadyLockedException;
import com.innercicle.annotations.RedissonLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RedissonLockAspect {

    private final RedissonClient redissonClient;

    @Around("@annotation(com.innercicle.annotations.RedissonLock)")
    public Object redissonLock(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        RedissonLock annotation = method.getAnnotation(RedissonLock.class);
        String lockKey =
            method.getName() + CustomSpringELParser.getDynamicValue(signature.getParameterNames(), joinPoint.getArgs(), annotation.value());

        RLock lock = redissonClient.getLock(lockKey);

        try {
            boolean lockable = lock.tryLock(annotation.waitTime(), annotation.leaseTime(), TimeUnit.MILLISECONDS);
            if (!lockable) {
                log.error("Lock 획득 실패={}", lockKey);
                throw new AlreadyLockedException("Lock 획득 실패했습니다.");
            }
            log.info("로직 수행");
            return joinPoint.proceed();
        } catch (InterruptedException e) {
            log.info("에러 발생 : {}", e.getMessage());
            throw e;
        } finally {
            log.info("락 해제");
            lock.unlock();
        }

    }

}