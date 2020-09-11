package com.spring.aop.helloworld;

public class ArithmeticCalculatorLoggingImpl implements ArithmeticCalculator {

	@Override
	public int add(int i, int j) {
		System.out.println("ATGUIGU->The mehtod add begins with["+i+","+j+"]");
		int result = i + j;
		System.out.println("ATGUIGU->The mehtod add ends with "+result);
		return result;
	}

	@Override
	public int sub(int i, int j) {
		System.out.println("ATGUIGU->The mehtod sub begins with["+i+","+j+"]");
		int result = i - j;
		System.out.println("ATGUIGU->The mehtod sub ends with "+result);
		return result;
	}

	@Override
	public int mul(int i, int j) {
		System.out.println("ATGUIGU->The mehtod mul begins with["+i+","+j+"]");
		int result = i * j;
		System.out.println("ATGUIGU->The mehtod mul ends with "+result);
		return result;
	}

	@Override
	public int div(int i, int j) {
		System.out.println("ATGUIGU->The mehtod div begins with["+i+","+j+"]");
		int result = i / j;
		System.out.println("ATGUIGU->The mehtod div ends with "+result);
		return result;
	}

}
