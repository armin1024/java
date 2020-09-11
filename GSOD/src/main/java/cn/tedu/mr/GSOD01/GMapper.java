package cn.tedu.mr.GSOD01;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Mapper组件输出的 key为处理之后的新行数据，value为空
 *      1. 在 Mapper组件中，每读取到一行数据，会执行一次 mao函数
 *      2. map()函数的执行是自动实现
 */
public class GMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
    //记录 map()函数被调用的次数 / 也可以通过该变量判断读取的行数
    private static int count = 0;
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        count++;
        //过滤掉前20行数据（这些数据不做处理）
        if (count <= 20) {
            return; //返回方法的调用处
        }

        //先获取读取的行数据，并转变为String
        String line = value.toString();
        //过滤掉空行数据
        if ("".equals(line))
            return;

        //去除标题行数据
        if (line.startsWith("USAF"))
            return;

        //从一行数据中获取USAF,WBAN,CTRY,LAT,LON
        //trim()方法，将字符串前后的空格，制表符，回车等字符串删除
        String usaf = line.substring(0, 6).trim(); //substring()方法包前不包后，所以要读取到下标6的位置
        String wban = line.substring(7, 12).trim();
        String ctry = line.substring(43, 47).trim();
        String lat = line.substring(57, 64).trim();
        String lon = line.substring(65, 73).trim();

        //去除经纬度为空行
        if ("".equals(lat))
            return;
        if ("".equals(lon))
            return;

        String newLine = usaf+","+wban+","+ctry+","+lat+""+lon;

        context.write(new Text(newLine), NullWritable.get());
    }
}
