package cn.tedu.mr.GSOD02;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class GDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
//        System.setProperties("hadoop.home.dir", "");
        Configuration conf = new Configuration();
        conf.set("mapreduce.framework.name", "yarn");
        conf.set("yarn.resourcemanager.hostname", "192.168.56.100");
        conf.set("yarn.nodemanager.aux-services", "mapreduce_shuffle");
        conf.set("fs.defaultFS", "hdfs://hadoop:9000");
        //跨平台调试 MapReduce任务是需要指定的参数
        conf.set("mapreduce.app-submission.cross-platform", "true");

        Job job = Job.getInstance(conf);

        job.setJar("E:\\WorkSpaces\\GSOD\\target\\GSOD-1.0-SNAPSHOT.jar"); //这里需要指定可执行的jar文件
        job.setJarByClass(cn.tedu.mr.GSOD02.GDriver.class);

        //设置 Mapper组件相关的内容
        job.setMapperClass(GMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        //设置 Reducer组件相关内容
        job.setReducerClass(GReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        //设置输入文件和输出文件的路径
        String inpath = "hdfs://192.168.56.100:9000/GSOD/gsod_2020.txt";
        String outpath = "hdfs://192.168.56.100:9000/GSOD/out4/";
        FileInputFormat.setInputPaths(job, new Path(inpath));
        FileOutputFormat.setOutputPath(job, new Path(outpath));

        job.waitForCompletion(true);
    }
}
