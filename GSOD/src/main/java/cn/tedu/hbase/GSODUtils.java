package cn.tedu.hbase;

/**
 * stations表对应的工具类
 * 该表中主要存放stations表的基本信息
 *      表名，列族，具体的列
 */
public class GSODUtils {
    public static final String TableName = "gsod";
    //列族
    public static final String famliy = "info";
    //具体的列 usaf, wban, ctry, lat, lon
    public static final String usaf = "usaf";
    public static final String wban = "wban";
    public static final String moda = "moda";//月，日
    public static final String temp = "temp";//平均气温
    public static final String visib = "visib";//平均可见度
    public static final String wdsp = "wdsp";//平均风速


    //保存具体列的数组
    public static final String[] cols = {usaf, wban, moda, temp, visib, wdsp};
}