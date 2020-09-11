package cn.tedu.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.mapreduce.LoadIncrementalHFiles;
public class HDFS2HBase {
    public static void main(String[] args) throws Exception {
        String path = "hdfs://hadoop:9000/GSOD/db1/";
        Configuration conf = HBaseUtils02.conf;
        Connection connection = HBaseUtils02.conn;
        TableName tbName = TableName.valueOf(GSODUtils.TableName);
        Admin admin = HBaseUtils02.admin;
        Table table = HBaseUtils02.table;
        LoadIncrementalHFiles load = new LoadIncrementalHFiles(conf);
        load.doBulkLoad(new Path(path), admin,table,connection.getRegionLocator(tbName));
    }
}
