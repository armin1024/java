package cn.tedu.hdfs;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HDFS的基本操作: 文件上传, 下载, 目录的创建等
 *
 *  操作HDFS文件系统:
 *      1. 创建配置信息对象
 *      2. 创建表示HDFS文件系统的对象(FileSystem)
 *      3. 通过IO/其他方式, 操作HDFS文件系统
 *      4. 关闭资源(关闭文件系统)
 */
public class HDFSUtils {
    /**
     * 将本地的文件上传到HDFS上
     * @param localPath 本地路径, 源文件路径
     * @param hdfsPath  hdfs路径, 目标路径
     */
    public static void put(String localPath, String hdfsPath) throws Exception {
        Configuration conf = new Configuration();
        conf.set("dfs.replication", "1");
        URI uri = new URI("hdfs://192.168.56.100:9000");
//        FileSystem fs = FileSystem.get(uri, conf);
        // 如果希望上传的文件的所属者为root
        FileSystem fs = FileSystem.get(uri, conf, "root");
        // 创建和本地文件系统对应的输入流
        InputStream in = new FileInputStream(localPath);
        // 创建和HDFS文件系统对应的输出流
        OutputStream out = fs.create(new Path(hdfsPath));
        // 通过流交换实现数据上传
        IOUtils.copy(in, out);
        fs.close();
    }

    /**
     * 将HDFS文件系统上的文件下载到本地
     * @param hdfsPath  源文件路径
     * @param localPath 目标文件路径
     */
    public static void get(String hdfsPath, String localPath) throws Exception {
        Configuration conf = new Configuration();
        conf.set("dfs.replication", "1");
        URI uri = new URI("hdfs://192.168.56.100:9000");
        FileSystem fs = FileSystem.get(uri, conf, "root");
        // 创建从HDFS文件系统上读取数据的输入流
        InputStream in = fs.open(new Path(hdfsPath));
        // 创建从本地文件系统上输出数据的输出流
        OutputStream out = new FileOutputStream(localPath);
        IOUtils.copy(in, out);
        fs.close();
    }

    public static void createDir(String path) throws Exception {
        Configuration conf = new Configuration();
        conf.set("dfs.replication", "1");
        URI uri = new URI("hdfs://192.168.56.100:9000");
        FileSystem fs = FileSystem.get(uri, conf, "root");

        boolean b = fs.mkdirs(new Path(path));

        System.out.println(b);

        fs.close();
    }

    // 作业要求1: 创建文件/目录的删除(强制删除为例)方法
    //              添加文件/目录的修改(名称, 路径) 方法
    //      要求2: 将每个方法中重复的内容(配置对象, 文件系统对象)提取, 并且应用

    //删除
    public static void deleteDir(String path) throws Exception {
        Configuration conf = new Configuration();
        conf.set("dfs.replication", "1");
        URI uri = new URI("hdfs://192.168.56.100:9000");
        FileSystem fs = FileSystem.get(uri, conf, "root");
        Path p = new Path(path);
        fs.delete(new Path(path), true);
        fs.close();
    }
}
