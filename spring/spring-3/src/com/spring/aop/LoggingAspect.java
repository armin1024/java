package com.spring.aop;

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

/**
 * ����ʹ�� @Order ע��ָ����������ȼ�, ֵԽС���ȼ�Խ��
 *
 */
@Order(2)
@Aspect
@Component
public class LoggingAspect {

	/**
	 * ����һ������, ���������������ʽ. һ���, �÷������ٲ���Ҫ��������Ĵ���
	 * ʹ��  @Pointcut �������������ʽ. 
	 * ���������ֱ֪ͨ��ʹ�÷����������õ�ǰ���������ʽ. 
	 */
	@Pointcut("execution(public int com.spring.aop.ArithmeticCalculator.*(..))")
	public void declareJoinPointExpression() {
		
	}
	
	/**
	 * �� com.spring.aop.ArithmeticCalculator �ӿڵ�ÿ��ʵ�����ÿһ��������ʼ֮ǰִ��һ�δ���
	 */
	@Before("declareJoinPointExpression()")
	public void beforeMethod(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName();
		Object [] args = joinPoint.getArgs();
		
		System.out.println("The method "+methodName+" begins with "+Arrays.asList(args));
	}
	
	/**
	 * �ڷ���ִ��֮��ִ�еĴ���. ���۸÷����Ƿ�����쳣
	 * @param joinPoint
	 */
	@After("declareJoinPointExpression()")
	public void afterMethod(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName();
		System.out.println("The method "+methodName+" ends");
	}
	
	/**
	 * �ڷ�������������ִ�еĴ���
	 * ����֪ͨ�ǿ��Է��ʵ������ķ���ֵ��! 
	 */
	@AfterReturning(value="declareJoinPointExpression()",
			returning = "result")
	private void afterReturning(JoinPoint joinPoint, Object result) {
		String methodName = joinPoint.getSignature().getName();
		System.out.println("The method "+methodName+" ends " +result);
	}
	
	/**
	 * ��Ŀ�귽�������쳣ʱ��ִ�еĴ���.
	 * ���Է��ʵ��쳣����. �ҿ���ָ�������ض��쳣ʱ��ִ��֪ͨ����
	 */
	@AfterThrowing(value="declareJoinPointExpression()",
			throwing = "ex")
	private void afterThowing(JoinPoint joinPoint, Exception ex) {
		String methodName = joinPoint.getSignature().getName();
		System.out.println("The method "+methodName+" occurs ex: " +ex);
	}
	
	/**
	 * ����֪ͨ��ҪЯ�� ProceedingJoinPoint ���͵Ĳ���.
	 * ����֪ͨ�����ڶ�̬�����ȫ����: ProceedingJoinPoint ���͵Ĳ������Ծ����Ƿ�ִ��Ŀ�귽��.
	 * �һ���֪ͨ�����з���ֵ, ����ֵ��ΪĿ�귽���ķ���ֵ
	 */
	@Around("declareJoinPointExpression()")
	private Object aroundMethod(ProceedingJoinPoint pjd) {

		Object result = null;
		String methodName = pjd.getSignature().getName();
		
		//ִ��Ŀ�귽��
		try {
			//ǰ��֪ͨ
			System.out.println("The method "+methodName+" begins with "+Arrays.asList(pjd.getArgs()));
			result = pjd.proceed();
			//����֪ͨ
			System.out.println("The method "+methodName+" ends with "+result);
		} catch (Throwable e) {	
			//�쳣֪ͨ
			System.out.println("The method "+methodName+" occurs exception: "+e);
			throw new RuntimeException(e);
		}
		//����֪ͨ
		System.out.println("The method "+methodName+" ends");
		
		return result;
	}
	
}
