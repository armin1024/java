package cn.tedu.lambda;

import java.util.ArrayList;
import java.util.List;

/**
 * 对集合中的数据实现过滤操作
 */
public class Demo01 {
    public static void main(String[] args) {
        MyList<Integer> myList = new MyList<>();
        myList.add(90);
        myList.add(21);
        myList.add(56);
        myList.add(1);
        myList.add(9);
        myList.add(0);
        myList.add(87);
        //过滤的规则 --> 不固定
        //过滤奇数，得到偶数
        F2 f2 = new F2();
        List<Integer> filter1 =  myList.filter(f2);
        System.out.println(filter1);
        /**
         * 通过匿名内部类实现
         */
        List<Integer> filter2 = myList.filter(new Function2() {
            @Override
            public boolean filter(Integer a) {
                if (a % 2 == 0)
                    return true;
                return false;
            }
        });
        System.out.println(filter2+"==========");
        /**
         * 通过lambda表达式实现
         */
        List<Integer> filter3 = myList.filter(x -> x % 2 == 0);
        System.out.println(filter3+"++++++++++");

    }
}
class  F2 implements Function2 {
    @Override
    public boolean filter(Integer a) {
        if (a % 2 == 0)
            return true;
        return false;
    }
}

/**
 * 自定义的集合类
 */
class MyList<Integer> extends ArrayList<Integer> {

    //自定义的过滤方法，返回值为：符合要求的新集合数据
    public List<Integer> filter(Function2 f2) {
        //定义一个返回的集合 -- 空集合
        List<Integer> list = new ArrayList<>();
        //在某个类的普通方法中，可以使用this引用
        for (Integer t : this) {
            if (f2.filter((java.lang.Integer) t)){
                list.add(t);
            }
        }
        return list;
    }
}

interface Function2 {
    boolean filter(Integer a);
}