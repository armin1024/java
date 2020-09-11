package com.spring.beans.collection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		Person p = (Person) ctx.getBean("person5");
		System.out.println(p);
		
		NewPerson newPerson = (NewPerson) ctx.getBean("newPerson");
		System.out.println(newPerson);
		
		DataSource dataSource = ctx.getBean(DataSource.class);
		System.out.println(dataSource);
	}
}
