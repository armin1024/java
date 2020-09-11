package com.spring.aop.xml;

import java.util.Arrays;

import javax.management.RuntimeErrorException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

public class LoggingAspect {

	public void beforeMethod(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName();
		Object [] args = joinPoint.getArgs();
		
		System.out.println("The method "+methodName+" begins with "+Arrays.asList(args));
	}

	public void afterMethod(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName();
		System.out.println("The method "+methodName+" ends");
	}

	private void afterReturning(JoinPoint joinPoint, Object result) {
		String methodName = joinPoint.getSignature().getName();
		System.out.println("The method "+methodName+" ends " +result);
	}

	private void afterThrowing(JoinPoint joinPoint, Exception ex) {
		String methodName = joinPoint.getSignature().getName();
		System.out.println("The method "+methodName+" occurs ex: " +ex);
	}

	private Object aroundMethod(ProceedingJoinPoint pjd) {

		Object result = null;
		String methodName = pjd.getSignature().getName();
		
		//执行目标方法
		try {
			//前置通知
			System.out.println("The method "+methodName+" begins with "+Arrays.asList(pjd.getArgs()));
			result = pjd.proceed();
			//后置通知
			System.out.println("The method "+methodName+" ends with "+result);
		} catch (Throwable e) {	
			//异常通知
			System.out.println("The method "+methodName+" occurs exception: "+e);
			throw new RuntimeException(e);
		}
		//后置通知
		System.out.println("The method "+methodName+" ends");
		
		return result;
	}
	
}
