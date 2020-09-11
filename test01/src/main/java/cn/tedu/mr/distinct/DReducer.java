package cn.tedu.mr.distinct;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class DReducer extends Reducer<Text, NullWritable, Text, NullWritable> {
    //在rudece（）函数执行之前，已经对 key合并完毕
    @Override
    protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        context.write(key, NullWritable.get());
    }
}
