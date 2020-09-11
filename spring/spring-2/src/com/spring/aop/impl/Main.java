package com.spring.aop.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		
		//1.创建 Spring 的 IOC 容器
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		//2.从 IOC 容器中获取 bean 的实例
		ArithmeticCalculator arithmeticCalculator = (ArithmeticCalculator) ctx.getBean("arithmeticCalculatorImpl");
		
		//3.使用 bean
		int result = arithmeticCalculator.add(3, 6);
		System.out.println("result: "+result);
		
		result = arithmeticCalculator.div(12, 0);
		System.out.println("result: "+result);
		
	}
	
}
