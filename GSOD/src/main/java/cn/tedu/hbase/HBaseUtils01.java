package cn.tedu.hbase;

import cn.tedu.pojo.Stations;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
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
public class HBaseUtils01 {
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
            table = conn.getTable(TableName.valueOf(StationsUtils.TableName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询 HBase中stations表所有的数据
     * @return
     */
    public static List<Stations> getDatas() throws IOException {
        List<Stations> list = new ArrayList<>(); //保存所有stations对象的集合

        //Get -- 通过单个行键来查询
        Scan scan = new Scan();
        ResultScanner results = table.getScanner(scan);
        Iterator<Result> it = results.iterator();
        while (it.hasNext()) {
            Stations s = new Stations();

            Result result = it.next(); //一行数据的集合
            List<Cell> cells = result.listCells();
            String rowkey = Bytes.toString((result.getRow()));

            byte[] lat_b = result.getValue(Bytes.toBytes(StationsUtils.famliy), Bytes.toBytes(StationsUtils.lat));
            byte[] lot_b = result.getValue(Bytes.toBytes(StationsUtils.famliy), Bytes.toBytes(StationsUtils.lon));

            String lat = Bytes.toString(lat_b);
            String lon = Bytes.toString(lot_b);

            s.setName(rowkey);
            String[] value = {lon, lat};
            s.setValue(value);

            list.add(s);
            /*System.out.print(rowkey);
            for (String col : StationsUtils.cols) {
                byte[] value_b = result.getValue(Bytes.toBytes(StationsUtils.famliy), Bytes.toBytes(col));
                String value = Bytes.toString(value_b);
                System.out.print(":"+col+":"+value);
            }
            System.out.println("");*/
            /*for (Cell cell : cells) {
                String famliy = Bytes.toString(CellUtil.cloneFamily(cell));
                String col = Bytes.toString(CellUtil.cloneQualifier(cell));
                String value = Bytes.toString(CellUtil.cloneValue(cell));
                System.out.println(famliy+","+col+","+value);
            }*/
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
            put.addColumn(Bytes.toBytes(StationsUtils.famliy),
                    Bytes.toBytes(StationsUtils.cols[i]),
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
        List<Stations> list = getDatas();
        System.out.println(list.size());
        //getDatas();
    }
}
