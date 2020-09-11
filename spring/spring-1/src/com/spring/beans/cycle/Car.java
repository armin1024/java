package com.spring.beans.cycle;

public class Car {

	@Override
	public String toString() {
		return "Car [brand=" + brand + "]";
	}

	public Car() {
		System.out.println("Car's Constructor...");
	}
	
	private String brand;
	
	public void setBrand(String brand) {
		System.out.println("setBrand...");
		this.brand = brand;
	}
	
	public void init() {
		System.out.println("init...");
	}
	
	public void destroy() {
		System.out.println("destroy...");
	}
	
}
