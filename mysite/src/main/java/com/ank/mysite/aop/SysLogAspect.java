package com.ank.mysite.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class SysLogAspect {
	
	@Pointcut("@annotation(com.ank.mysite.annotation.Syslog)")
	public void pointcut(){
	}

	@AfterThrowing(pointcut="pointcut()", throwing="e")
	public void doAfterThrowing(JoinPoint point, Throwable e) {
		System.out.println("doAfterThrowing");
	}

	@Around("pointcut()")
	public void doAround(ProceedingJoinPoint point) throws Throwable {
		System.out.println("doAround");
	}

}
