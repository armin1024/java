package cn.tedu.mr.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
/***
 *  （1）对文件中出现单词的次数，词频统计
 */
public class WCDriver {
    public static void main(String[] args) throws Exception {
        // 创建配置对象
        Configuration conf = new Configuration();
        //conf.set("hadoop.home.dir", "E:\\DevelopTools\\apache\\hadoop-2.8.5");
        // 创建 Job
        Job job = Job.getInstance(conf);

        //给 Job任务指定 Mapper组件的相关内容（指定执行 Mapper组件的 class文件，输入，输出的 key-value类型）
        job.setMapperClass(WCMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        //给 Job任务指定 Reducer组件的相关内容（执行class文件，输入输出的 k-v类型）
        job.setReducerClass(WCReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);


        //指定输入文件，输出文件路径
        FileInputFormat.setInputPaths(job, new Path("src/main/resources/wc.txt"));
        FileOutputFormat.setOutputPath(job, new Path("out/wc1"));

        //提交 Job任务
        job.waitForCompletion(true);
    }
}
