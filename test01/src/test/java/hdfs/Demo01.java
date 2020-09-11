package hdfs;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 实现HDFS文件上传下载
 */
public class Demo01 {
    // 主函数的生成, psvm  或者 main
    public static void main(String[] args) throws Exception {
        // sout 生成 System.out.println()
        // 万能键 : Alt + Enter
//         单行注释: Ctrl + /
        // 多行注释: 选中多行, Ctrl + Shift + /
        // 快速生成变量名称: Ctrl+Alt+V

        put();
    }


    /**
     * 实现本地文件上传到HDFS文件系统中
     *      1. 创建配置对象
     *      2. 指定HDFS文件系统/文件系统的路径
     *      3. 读取本地文件, 将数据写出到HDFS文件系统
     */
    public static void put() throws Exception {
        // 创建配置信息对象
        Configuration conf = new Configuration();
        // hdfs://192.168.56.100:9000
        FileSystem fs = FileSystem.get(
                new URI("hdfs://192.168.56.100:9000"), conf
        );
        // 读取本地文件的输入流
        InputStream in = new FileInputStream("G:/a.txt");
        // 获取和HDFS文件系统的输出流, 是通过文件系统对象来打开
        OutputStream out = fs.create(new Path("/hello.txt"));
        // 将输入流的数据拷贝给输出流 -- 通过工具类实现
        IOUtils.copy(in, out);

        // 关闭文件系统
        fs.close();
    }
}
