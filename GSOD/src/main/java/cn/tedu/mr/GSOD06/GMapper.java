package cn.tedu.mr.GSOD06;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * 测试 usaf-wban 是否唯一的，能不能当作 hbase表中的 rowkey
 */
public class GMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        //此时out1/part-r-00000文件中的数据已经是一个格式化的数据
        //字段和自动之间都是使用"，"进行分割的，那么就可以使用split(",")
        String[] strs = line.split("\t");
        //将usaf和wban拼接起来
        String newLine = strs[0]+"-"+strs[1];
        context.write(new Text(newLine), NullWritable.get());
    }
}