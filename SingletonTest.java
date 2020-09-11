package com.singleton;
/**
 * 单例设计模式：
 * 1. 所谓类的单例设计模式，就是采取一定的方法保证在整个的软件系统中，对某个类只能存在一个对象实例。
 *
 * 2. 如何实现？
 * 	 饿汉式  vs 懒汉式
 *
 * 3. 区分饿汉式 和 懒汉式
 *   饿汉式：
 *   	坏处：对象加载时间过长。
 *   	好处：饿汉式是线程安全的
 *
 *   懒汉式：好处：延迟对象的创建。
 * 		  目前的写法坏处：线程不安全。--->到多线程内容时，再修改
 */
public class SingletonTest {
    public static void main(String[] args) {
        Bank b1 = Bank.getInstance();
        Bank b2 = Bank.getInstance();

        System.out.println(b1==b2); //true: b1的地址与b2相等
    }
}

/**
 * 单例模式：饿汉式
 */
class Bank{

    //1.私有化构造器
    private Bank(){

    }

    //2.内部创建类的对象
    //4.要求此对象必须声明为静态的
    private static Bank instance = new Bank();

    //3.提供公共的静态的方法，返回类的对象
    public static Bank getInstance(){
        return instance;
    }
}

/**
 * 懒汉式
 */
class Order{

    private Order(){

    }

    private static Order instance = null;

    public static Order getInstance(){
        if (instance == null){
            //此处解决线程安全：效率较高的一种方法
            synchronized (Order.class){
                if (instance == null){
                    instance = new Order();
                }
            }
        }
        return instance;
    }
}
