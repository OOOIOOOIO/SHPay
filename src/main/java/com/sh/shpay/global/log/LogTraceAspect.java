package com.sh.shpay.global.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect // advice + pointcut = advisor
@Component
public class LogTraceAspect {

    @Around("@annotation(com.sh.shpay.global.log.LogTrace)")
    public Object doLogTrace(ProceedingJoinPoint joinPoint) throws Throwable{
        StopWatch stopWatch = new StopWatch();

        log.info("[ 트랜잭션 시작 ]");

        stopWatch.start();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        Class returnType = signature.getReturnType();
        log.info("=> 실행 메서드: {} | return type = {}", methodName, returnType);

        Object proceed = joinPoint.proceed();

        stopWatch.stop();

        long totalTimeMillis = stopWatch.getTotalTimeMillis();
        log.info("[ 트랜잭션 종료 | 실행시간 = {}]", totalTimeMillis);


//        log.info("실행 메서드: {} | 실행시간 = {}ms | return type = {}", methodName, totalTimeMillis, returnType);


        return proceed;
    }
}
