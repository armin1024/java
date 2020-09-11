package hdfs;

import cn.tedu.hdfs.HDFSUtils;
import com.sun.javaws.IconUtil;
import org.apache.commons.collections.map.HashedMap;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws  Exception{
        //HDFSUtils.put("G:/a.txt", "/new.txt");
        Map<String, String> map = new HashMap();
        map.put("zhangsan", "12");
        map.put("lisi", "15");
        map.put("zhangsan", "19");

        System.out.println(map);

        //数组    在定义数组时，数组的长度必须是确定的  Map<单词, List>
/*
        int[] arr = {1, 43, 65, };
        for(int i=0; i<arr.length;i++){
            int num = arr[i];
            System.out.println(num);
        }
        //格式： for(int num : arr)    for(为集合中每个元素定义的变量名称 ： 原集合的变量)
        for(int num : arr){
            System.out.println(num);
        }*/
    }
}
