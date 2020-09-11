package com.spring.beans.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * ��̬��������: ֱ�ӵ���ĳһ����ľ�̬�����Ϳ��Է��� Bean ��ʵ��
 */
public class StaticCarFactory {

	public static Map<String, Car> cars = new HashMap<String, Car>();
	
	static {
		cars.put("audi", new Car("audi", 300000));
		cars.put("ford", new Car("ford", 400000));
	}
	
	//��̬��������
	public static Car getCar(String name) {		
		return cars.get(name);
	}
	
}
