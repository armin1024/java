package cn.tedu.hbase;

/**
 * stations表对应的工具类
 * 该表中主要存放stations表的基本信息
 *      表名，列族，具体的列
 */
public class StationsUtils {
    public static final String TableName = "stations";
    //列族
    public static final String famliy = "info";
    //具体的列 usaf, wban, ctry, lat, lon
    public static final String usaf = "usaf";
    public static final String wban = "wban";
    public static final String ctry = "ctry";
    public static final String lat = "lat";
    public static final String lon = "lon";

    //保存具体列的数组
    public static final String[] cols = {usaf, wban, ctry, lat, lon};
}
