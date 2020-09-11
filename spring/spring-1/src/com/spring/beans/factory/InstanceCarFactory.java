package com.spring.beans.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * ʵ����������: ʵ�������ķ���. ������Ҫ������������, �ڵ��ù�����ʵ������������ bean ��ʵ��
 * @author 10233
 *
 */
public class InstanceCarFactory {

	private Map<String, Car> cars = null;
	
	public InstanceCarFactory() {
		cars = new HashMap<String, Car>();
		cars.put("audi", new Car("audi", 300000));
		cars.put("ford", new Car("ford", 400000));
	}
	
	public Car getCar(String brand) {
		return cars.get(brand);
	}
	
}
