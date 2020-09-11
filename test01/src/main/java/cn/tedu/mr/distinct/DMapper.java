package cn.tedu.mr.distinct;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/***
 *  hadoop中的 NullWritable -->null（不输出数据）
 */

public class DMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] strs = line.split(" ");        //用空格切割数据
        for (String str : strs){
            context.write(new Text(str), NullWritable.get());
        }
    }
}
