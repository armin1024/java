package cn.tedu.mr.distinct;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
/**
 *  （2）文件中出现过哪些单词，去重
 */
public class DDriver {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setMapperClass(DMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        job.setReducerClass(DReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        //指定输入文件，输出文件路径
        FileInputFormat.setInputPaths(job, new Path("src/main/resources/wc.txt"));
        FileOutputFormat.setOutputPath(job, new Path("out/wc10"));

        //提交 Job任务
        job.waitForCompletion(true);

    }
}
