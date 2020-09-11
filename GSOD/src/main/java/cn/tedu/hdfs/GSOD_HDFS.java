package cn.tedu.hdfs;

        import org.apache.commons.io.IOUtils;
        import org.apache.hadoop.conf.Configuration;
        import org.apache.hadoop.fs.FileSystem;
        import org.apache.hadoop.fs.Path;

        import java.io.*;
        import java.net.URI;
        import java.net.URISyntaxException;

public class GSOD_HDFS {
    private static Configuration conf = null;
    private static FileSystem fs = null;

    static {
        try {
            conf = new Configuration();
            // 设置HDFS集群中文件块的副本数量
            conf.set("dfs.replication", "1");
            // 创建HDFS文件系统对象
            URI uri= new URI("hdfs://192.168.56.100:9000");
            fs = FileSystem.get(uri, conf);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * 在HDFS上创建目录
     * @param hdfsPath
     */
    public static void createDir(String hdfsPath){
        try {
            fs.mkdirs(new Path(hdfsPath));
            System.out.println(hdfsPath + " 目录创建成功!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将 isd-history.txt 文件上传到 HDFS 集群中的 /GOSD/目录下
     * 上传之后, 数据的副本为1
     */
    public static void putFile(String localPath, String hdfsPath) {
        InputStream in = null;
        OutputStream out = null;
        try {
            // 指定输入文件的输入流-->本地
            in = new FileInputStream(localPath);
            // 输出流 -- > HDFS
            out = fs.create(new Path(hdfsPath));

            // 交换输入流和输出流中的数据
            IOUtils.copy(in, out);

            System.out.println("文件上传到 "+hdfsPath+" 成功!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            // 关闭资源
            if(out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    out = null;
                }
            }
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    in = null;
                }
            }
        }
    }

    /**
     * 将本地一个目录中的所有文件上传到hdfs的目录中
     * @param localDir 本地目录名称  gsod_2020/00010-999.gz
     * @param hdfsDir  hdfs目录名称  /gsod/00010-999.gz
     *                 hdfsDir 的写法以:  /gsod/
     */
    public static void putFiles(String localDir, String hdfsDir){
        File dir = new File(localDir); // 此时该File对象表示为一个目录
        if(dir.isDirectory()){
            File[] files = dir.listFiles();
            for (File file : files) {
                putFile(file.getAbsolutePath(), hdfsDir+file.getName());
            }
        }
    }

    public static void main(String[] args) {
//        createDir("/GSOD/");
//        putFile("G:\\isd-history.txt", "/GSOD/isd-history.txt");
        putFile("G:\\distence", "/GSOD/distence");
    }
}
