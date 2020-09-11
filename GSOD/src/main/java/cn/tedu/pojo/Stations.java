package cn.tedu.pojo;   //该包中主要存放实体类，是和前台图表或者后台数据库表一一对应的

import java.util.Arrays;

/**
 * 和后台 HBase数据库中 stations表对应的实体类
 * 每一个 stations对象存储一条记录
 */
public class Stations {
    private String name; //usaf-wban: rowkey
    private String[] value; //存放lan, lat

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getValue() {
        return value;
    }

    public void setValue(String[] value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Stations{" +
                "name='" + name + '\'' +
                ", value=" + Arrays.toString(value) +
                '}';
    }
}
