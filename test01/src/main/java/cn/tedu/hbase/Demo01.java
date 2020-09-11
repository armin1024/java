package cn.tedu.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.w3c.dom.html.HTMLCollection;

import java.io.IOException;

public class Demo01 {
    public static void main(String[] args) throws IOException {
//        String[] famlies = {"base_info", "score_info"};
//        deleteTable("stu1");
//        createTable("stu1", famlies);
//        // zhangsan001, base_info:id:001, base_info:name:张三, base_info:age:14, score_info:math:90, score_info:china:80
//        putData("stu1","zhangsan001","base_info", "id", "001");
//        putData("stu1","zhangsan001","base_info", "name", "张三");
//        putData("stu1","zhangsan001","base_info", "age", "14");
//        putData("stu1","zhangsan001","score_info", "math", "90");
//        putData("stu1","zhangsan001","score_info", "china", "80");

        getData("stu1", "zhangsan001");

    }

    private static Configuration conf = null;
    private static Connection conn = null;
    // 负责表级别的操作
    private static Admin admin = null;
    // 负责数据级别的操作
//    private static Table table = null;
    static{  // 共享
        try {
            conf = HBaseConfiguration.create();
            conf.set("hbase.zookeeper.quorum", "hadoop");
            conn = ConnectionFactory.createConnection(conf);
            admin = conn.getAdmin();
//            table = conn.getTable()
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据rowkey进行数据查询
     * @param tablename
     * @param rowkey
     */
    public static void getData(String tablename, String rowkey) throws IOException {
        Table table = conn.getTable(TableName.valueOf(tablename));
        // Get -- 通过单个行键查询
        // Scan -- 通过rowkey的范围, 全表扫描
        Get get = new Get(Bytes.toBytes(rowkey));
        // 自行查询, 得到结果集对象
        Result result = table.get(get);
        for (Cell cell : result.listCells()) {
            // 通过CellUtil工具类查询得到的数据都是byte[], 需要转变为String
            // CellUtil.cloneFamily(cell)  --> byte[]
            String family = Bytes.toString(CellUtil.cloneFamily(cell));
            String col = Bytes.toString(CellUtil.cloneQualifier(cell));
            String value = Bytes.toString(CellUtil.cloneValue(cell));
            System.out.println(family+" : "+col+" : "+value);
        }
        table.close();
    }


    /**
     * 如果知道列族和列以及列的具体指之间的对应关系, 可以实现一次一/多条条数据的插入
     * 插入一列数据
     * @param tableName
     * @param rowkey
     * @param famliy
     * @param cols
     * @param value
     */
    public static void putData(String tableName, String rowkey, String famliy, String cols, String value) throws IOException {
        Table table = conn.getTable(TableName.valueOf(tableName));
        // 要将数据插入表中, 需要先将数据封装成一个Put对象
        Put put = new Put(Bytes.toBytes(rowkey));
        put.addColumn(Bytes.toBytes(famliy), Bytes.toBytes(cols), Bytes.toBytes(value));
        // 将put对象中的数据插入表中
        table.put(put);

        table.close();
    }


    /**
     *
     * @param tableName 表名
     * @param famlies   列族信息
     */
    public static void createTable(String tableName, String[] famlies) throws IOException {
        // 通过指定表名, 获取表名描述器
        TableName tName = TableName.valueOf(tableName);
        // 创建表描述器
        HTableDescriptor tableDesc = new HTableDescriptor(tName);
        for (String famly : famlies) {
            // hbase中存储数据时, 都是以字节数组的形式进行存储
            byte[] byte_famly = Bytes.toBytes(famly);
            // 为每一个列族创建一个列族描述器
            HColumnDescriptor colDes = new HColumnDescriptor(byte_famly);
            // 将列族描述器添加到表描述器上
            tableDesc.addFamily(colDes);
        }
        // 通过表管理器创建表
        admin.createTable(tableDesc);
        System.out.println(tableName+" 表创建成功!");

    }

    /**
     * 查询hbase中的所有表名
     * @throws IOException
     */
    public static void listTables() throws IOException {

        TableName[] tableNames = admin.listTableNames();
        for (TableName name : tableNames) {
            String s = name.toString();
            System.out.println(s);
        }

    }

    /**
     * 删除表: 首先要禁用表, 然后在删除
     * @param tablename
     */
    public static void deleteTable(String tablename){
        try {
            admin.disableTable(TableName.valueOf(tablename));
            admin.deleteTable(TableName.valueOf(tablename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param tablename 要修改的表名
     * @param famliy 要修改的列族
     * @param option  要执行的操作: add, delete
     */
    public static void modifhTable(String tablename, String famliy, String option) throws IOException {
        if("add".equals(option))
            admin.addColumnFamily(TableName.valueOf(tablename), new HColumnDescriptor(Bytes.toBytes(famliy)));
        else if("delete".equals(option))
            admin.deleteColumn(TableName.valueOf(tablename), Bytes.toBytes(famliy));
    }
}
