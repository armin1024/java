package cn.tedu.mr.GSOD03;

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
        Job job = Job.getInstance(conf);

        //设置 Mapper组件相关的内容
        job.setMapperClass(GMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        //设置输入文件和输出文件的路径
        String inpath = "hdfs://192.168.56.100:9000/GSOD/distence";
        String outpath = "hdfs://192.168.56.100:9000/GSOD/out4/";
        FileInputFormat.setInputPaths(job, new Path(inpath));
        FileOutputFormat.setOutputPath(job, new Path(outpath));

        job.waitForCompletion(true);
    }
}
