package cn.tedu.mr.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
*  MapReduce任务中，Mapper组件的输出就是 Reducer组建的输入
*  所以，Reducer组件的输入的 key-value类型必须和 Mapper组件的输出 key-value类型保持一致
*
*           在当哪里中，我们希望最终的输出结果为（Java，23）
* Reducer组件中也有四个参数：
*       前两个表示数据输入到 Reducer组件时 k-v的类型（要和 Mapper组件的输出保持一致）
*       后两个表示数据输出到结果文件中时，k-v的类型
* */

public class WCReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    /**
     * 在 Reducer组件中，每个类型的 key，会调用一次 reduce()函数
     *  再将数据由 Mapper组件输出到 Reducer组件输入的过程中，数据会根据 key值，将对应的 value合并
     *  合并为（key,[v1,v2,v3,v4,....]）
     * @param key
     * @param values
     * @param context       用来将 reducer组件的计算结果输出到结果文件
     * @throws IOException
     * @throws InterruptedException
     * */
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
/*        System.out.println(key.toString());
        for (IntWritable value : values){
            System.out.print(value+",");
        }
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");*/

        int sum = 0;    //定义该单词最终出现的次数
        for (IntWritable value : values){
            //将IntWritable --> int
            sum += value.get();
        }
        // context
        context.write(key, new IntWritable(sum));
    }
}
