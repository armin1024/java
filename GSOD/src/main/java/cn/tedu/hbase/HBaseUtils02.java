package cn.tedu.hbase;

import cn.tedu.pojo.GSOD;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 和stations表对应的操作的工具类
 *  向stations中插入数据，查询stations表中的数据
 */
public class HBaseUtils02 {
    public static Configuration conf = null;
    public static Connection conn = null;
    public static Admin admin = null;
    public static Table table = null;
    static {
        try {
            conf = HBaseConfiguration.create();
            conf.set("dfs.replication", "1");
            conf.set("hbase.zookeeper.quorum", "hadoop");
            conn = ConnectionFactory.createConnection(conf);
            admin = conn.getAdmin();
            table = conn.getTable(TableName.valueOf(GSODUtils.TableName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询 HBase中stations表所有的数据
     * @return
     */
    public static List<GSOD> getDatas() throws IOException {
        List<GSOD> list = new ArrayList<>(); //保存所有stations对象的集合

        //Get -- 通过单个行键来查询
        Scan scan = new Scan();
        ResultScanner results = table.getScanner(scan);
        Iterator<Result> it = results.iterator();
        while (it.hasNext()) {
            GSOD s = new GSOD();

            Result result = it.next(); //一行数据的集合
//            List<Cell> cells = result.listCells();
            String rowkey = Bytes.toString((result.getRow()));

            System.out.print(rowkey);
//            byte[] a = result.getValue(Bytes.toBytes(GSODUtils.famliy), Bytes.toBytes(GSODUtils.usaf));
//            byte[] b = result.getValue(Bytes.toBytes(GSODUtils.famliy), Bytes.toBytes(GSODUtils.wban));
            byte[] c = result.getValue(Bytes.toBytes(GSODUtils.famliy), Bytes.toBytes(GSODUtils.moda));
            byte[] d = result.getValue(Bytes.toBytes(GSODUtils.famliy), Bytes.toBytes(GSODUtils.temp));
            byte[] e = result.getValue(Bytes.toBytes(GSODUtils.famliy), Bytes.toBytes(GSODUtils.visib));
            byte[] f = result.getValue(Bytes.toBytes(GSODUtils.famliy), Bytes.toBytes(GSODUtils.wdsp));

//            String usaf = Bytes.toString(a);
//            String wban = Bytes.toString(b);
            String moda = Bytes.toString(c);
            String temp = Bytes.toString(d);
            String visib = Bytes.toString(e);
            String wdsp = Bytes.toString(f);

            s.setName(rowkey);
            String[] value = {moda, temp, visib, wdsp};
            s.setValue(value);

            list.add(s);
//            for (String col : GSODUtils.cols) {
////                byte[] value_b = result.getValue(Bytes.toBytes(GSODUtils.famliy), Bytes.toBytes(col));
////                String value = Bytes.toString(value_b);
////                System.out.print(":"+col+":"+value);
////            }
//            System.out.println("");
//            for (Cell cell : cells) {
//                String famliy = Bytes.toString(CellUtil.cloneFamily(cell));
//                String col = Bytes.toString(CellUtil.cloneQualifier(cell));
//                String value = Bytes.toString(CellUtil.cloneValue(cell));
//                System.out.println(famliy+","+col+","+value);
//            }
        }
        results.close();
        return list;
    }

    /**
     * 只要指定的values的长度为5，就可以将一条数据插入进去
     * @param values
     */
    public static void putData(String[] values) {
        String rowkey = values[0]+"-"+values[1];
        Put put = new Put(Bytes.toBytes(rowkey));
        for (int i=0; i<values.length; i++) {
            put.addColumn(Bytes.toBytes(GSODUtils.famliy),
                    Bytes.toBytes(GSODUtils.cols[i]),
                    Bytes.toBytes(values[i]));
        }

        try {
            table.put(put); //插入一条数据
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 一次性插入多条数据到hbase表中
     */
    public static void putData(List<Put> puts){
        try {
            table.put(puts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        List<GSOD> list = getDatas();
        System.out.println(list.size());
    }
}
