package cn.tedu.mr.GSOD05;

import cn.tedu.hbase.GSODUtils;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class GMapper extends Mapper<LongWritable, Text, ImmutableBytesWritable, Put> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] strs = line.split(",");
        //指定行键
        String row = strs[0]+"-"+strs[1];
        //创建字节数组，使用ImmutableBytesWritable对象当作行键
        ImmutableBytesWritable rowkey = new  ImmutableBytesWritable(row.getBytes());
        // 创建Put对象, 用于封装每条数据到Hbase中
        Put put = new Put(row.getBytes());

        // 将其他数据插入到Put对象
        for (int i=0; i<strs.length; i++) {
            put.addColumn(Bytes.toBytes(GSODUtils.famliy),
                    Bytes.toBytes(GSODUtils.cols[i]),
                    Bytes.toBytes(strs[i]));
        }
        // 将行键当做key, Put对象当做value输出
        context.write(rowkey, put);
    }
}
