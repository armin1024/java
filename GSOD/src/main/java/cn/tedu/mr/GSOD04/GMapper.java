package cn.tedu.mr.GSOD04;

import cn.tedu.hbase.GSODUtils;
import cn.tedu.hbase.HBaseUtils02;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Mapper组件输出的key为处理之后的新行数据, value为空
 */
public class GMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
    static List<Put> list = new ArrayList<>();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] strs = line.split(",");
        String rowkey = strs[0]+"-"+strs[1];
        Put put = new Put(Bytes.toBytes(rowkey));
        for (int i=0; i<strs.length; i++) {
            put.addColumn(Bytes.toBytes(GSODUtils.famliy),
                    Bytes.toBytes(GSODUtils.cols[i]),
                    Bytes.toBytes(strs[i]));
        }
        list.add(put);
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        HBaseUtils02.putData(list);
    }
}
