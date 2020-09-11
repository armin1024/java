package cn.tedu.mr.GSOD04;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class GDriver {
    public static void main(String[] args) throws Exception {
//        System.setProperty("hadoop.home.dir", "");
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        // 设置Mapper组件的相关内容
        job.setMapperClass(GMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputKeyClass(NullWritable.class);

        // 设置输入文件和输出文件的路径
        String inpath = "hdfs://192.168.56.100:9000/GSOD/db_gsod.txt";
        String outpath = "hdfs://192.168.56.100:9000/GSOD/out3/";
        FileInputFormat.setInputPaths(job, new Path(inpath));
        FileOutputFormat.setOutputPath(job, new Path(outpath));

        job.waitForCompletion(true);
    }
}
