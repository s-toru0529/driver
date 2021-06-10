package com.tool.test.driver.application.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspects {
  // Before
  @Before("execution(* *..*.*Controller.*(..))")
  public void beforeController(JoinPoint joinPoint) {
    log.info("Controller開始:" + joinPoint.getSignature());
  }

  // After
  @After("execution(* *..*.*Controller.*(..))")
  public void afterontroller(JoinPoint joinPoint) {
    log.info("Controller終了:" + joinPoint.getSignature());
  }
}
