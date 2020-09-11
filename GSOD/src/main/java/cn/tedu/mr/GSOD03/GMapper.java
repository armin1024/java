package cn.tedu.mr.GSOD03;

import cn.tedu.hbase.HBaseUtils01;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Mapper组件输出的 key为处理之后的新行数据，value为空
 */
public class GMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] strs = line.split(",");
        HBaseUtils01.putData(strs);
    }
}
