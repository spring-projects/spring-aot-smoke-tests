package com.example.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import org.springframework.stereotype.Component;

@Aspect
@Component
public class TestAspect {

	@Around("pointcut()")
	public Object alterReturnValue(ProceedingJoinPoint joinPoint) throws Throwable {
		joinPoint.proceed();
		return "A-from-aspect";
	}

	@Pointcut("execution(* com.example.aspect.Test*.methodA(..))")
	private void pointcut() {
	}

}
