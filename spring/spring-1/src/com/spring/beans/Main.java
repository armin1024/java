package com.spring.beans;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		HelloWord hw = new HelloWord();
//		hw.setName("ARMIN");
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		HelloWorld hw = (HelloWorld) ctx.getBean("helloWorld");

//		HelloWorld hw = ctx.getBean(HelloWorld.class);
		System.out.println(hw);
//		
//		hw.hello();
		
		Car car = (Car) ctx.getBean("car");
		System.out.println(car);
		
		car = (Car) ctx.getBean("car2");
		System.out.println(car);
		
		Person p = (Person) ctx.getBean("person");
		System.out.println(p);
	}

}
