package cn.tedu.mr.wordcount;

import java.io.*;
import java.util.*;

/**
 *使用Java程序实现词频统计
 *      IO流：
 *          字符输入流，字符输出流  --> 处理文本文件的时候，使用字符流
 *          字节输入流，字节输出流  --> 使用字节流可以处理所有的文件
 *                                     字符流处理不了的文件，图片，视频，音频等
 *          缓冲流：读取 / 写出数据时，底层实现（CPU）和字符流 / 字节流，效率会更高
 *      字节和字符关系：
 *          字符 = 字节 + 字符集
 *          文本文件 --> 字符 / 字符集
 */

public class WordCountJava {
    public static void main(String[] args) throws Exception {
        //1.获取和该文件的输入流
        //2.读取文件中的每一行数据
        //3.按照空格切分，获取改行中的所有单词，并存储在容器
        //4.重复读取，重复执行 2-3
        //5.对该容器中的所有数据进行统计，得到结果

        //使用字符的输入流读取文件
        Reader reader = new FileReader("src/main/resources/wc.txt");
        //使用缓冲流，效率会更高，API更高级（人性化）
        BufferedReader bufr = new BufferedReader(reader);

        //定义保存单词和出现次数的容器 --> Map<String, List<Integer>>
        Map<String, List<Integer>> map = new HashMap<>();


        String line = bufr.readLine();
        while(line != null){

            //line就是表示读取得到每一行数据
            //按照空格切分，得到每一个单词，并返回一个数组
            String[] words = line.split(" ");
            //遍历数组，获取每一个单词
            for(String word : words){   //增强 for 循环
                //将单词存放到map容器中之前，需要判断当前map容器中是否存在该单词
                if(map.containsKey(word)){//判断当前map中是否包含key值
                    //如果存在，只需要将对应的 value 中集合添加 1
                    List<Integer> list = map.get(word);
                    list.add(1);
                    map.put(word, list);
                }else{
                    //如果不存在，需要创建一个集合，在集合中添加 1
                    List<Integer> list = new ArrayList<>();
                    list.add(1);
                    map.put(word, list);
                }
            }

            line = bufr.readLine();
        }
        System.out.println(map);
        //map中的key是唯一的
        Set<String> keys = map.keySet();//获取map容器中所有key
        for(String key : keys){
            List<Integer> list = map.get(key);
            int count = list.size();
            System.out.println("单词 "+key+" ,共出现了 "+count+" 次！");
        }
        // 关闭资源
        bufr.close();
        reader.close();
    }
}
