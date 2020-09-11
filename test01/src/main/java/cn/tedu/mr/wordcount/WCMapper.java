package cn.tedu.mr.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 *  Mapper组件中四个参数：
 *  数据输入到Mapper组件时，key，value的类型（通常都是LongWritable，Text）
 *      1.当前行在文件中的字符偏移量（该行数据在文件中是从第多少个字符开始） LongWritable --> long
 *      2.代表文件中读取得到的数据（一行）类型 Text --> String
 *
 *  数据从Mapper组件输出时，key，value的类型（根据业务逻辑决定）
 *      3.Mapper组件输出的key 的类型
 *      4.Mapper组件输出的value 的类型
 *
 *      在词频统计中，希望 Mapper输出的结果为（单词，1）
 */

public class WCMapper extends Mapper<LongWritable, Text, Text,IntWritable> {
    /**
     * 没获取得到一行数据时，会自动执行一次map函数
     * @param key   实际字符偏移量
     * @param value 实际读取到的每一行数据的具体值
     * @param context   用来将结果输出的组件
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
//        System.out.println(key);
//        System.out.println(value);
        //获取读到的每行数据 --> value，将 Text类型的数据装变为String
        String line = value.toString();
        //按照空格切割字符串，最终得到一个String[]
        String[] strs = line.split(" ");

        //因为希望输出的最终结果为（单词， 1），此时就需要获取数组中的每个单词
        for (String word : strs){
            //将 Mapper组件中的计算结果输出是通过 context组件来实现的
            context.write(new Text(word), new IntWritable(1));
        }
    }
}
