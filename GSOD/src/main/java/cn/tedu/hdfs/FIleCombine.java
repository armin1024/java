package cn.tedu.hdfs;

import java.io.*;
import java.util.zip.GZIPInputStream;

/**
 * 使用Java程序处理GSOD气象项目数据
 */
public class FIleCombine {
    public static void main(String[] args) throws Exception {
        String inputPath = "E:\\gsod_2020";
        String outputPath = "E:\\gsod_2020.txt";

        // 读取数据时使用的输入流
        FileInputStream fis = null; // 直接读取压缩文件的字节输入流
        GZIPInputStream gis = null; // 对压缩文件进行解压缩
        InputStreamReader isr = null;   // 将字节流转换为字符流(转换流：字符流)
        BufferedReader bufr = null; // 使用缓冲字符流读取文本文件数据

        File fileDir = new File(inputPath);  // 此时fileDir表示为一个目录
        File outFile = new File(outputPath);
        // 写出文件是使用的输出流
        FileWriter fw = new FileWriter(outFile);
        BufferedWriter bufw = new BufferedWriter(fw);

        int filecount = 0;  // 处理的文件数量
        int datacount = 0;  // 处理的数据数量

        // fileDir.listFiles();    获取该目录下所有的子文件,每个子文件又是一个File对象
        // 当for循环执行完成时，表示整个目录中的文件都读取完成了
        for (File file : fileDir.listFiles()) { // 对该目录下的所有子文件进行遍历
            filecount++;    // 文件计数器
            fis = new FileInputStream(file);
            gis = new GZIPInputStream(fis);
            isr = new InputStreamReader(gis);
            bufr = new BufferedReader(isr);

            String line = "";
            while((line = bufr.readLine()) != null){   // 当while循环执行完成之后，表示读取完一个文件中的数据
                if(line.startsWith("STN"))
                    continue;  // 如果是标题行，直接读取下一行数据

                datacount++;    // 行计数器

                // 截取 STN   官方文档中的位置1-6    处理的字符下标 0-5
                // 在使用substring方法时，包前不包后， 截取字段数值使用的字符的下标为： 0-6
                String usaf = line.substring(0, 6).trim();
                // 截取 WBAN  官方文档：8-12   字符下标：7-11  实际处理的字符下标：7-12
                String wban = line.substring(7, 12).trim();
                // 截取 YEARMODE  官方文档：15-22  字符下标：14-21   实际处理的字符下标：14-22
                String mode = line.substring(19, 22).trim();
                // temp  官方文档：25-30  字符下标：24-29   实际处理字符下标：24-30
                String temp = line.substring(24, 30).trim();
                // 平均可见度（999.9表示缺失数据）
                String visib = line.substring(69, 73).trim();
                // 平均风速（999.9表示缺失数据）
                String wdsp = line.substring(79, 83).trim();

                // 拼接新行数据
                String newLine = usaf+","+wban+","+mode+","+temp+","+visib+","+wdsp;

                // 将新行数据输出到结果文件中
                bufw.write(newLine);
                bufw.newLine();
            }

            // 关闭资源
            bufr.close();
            isr.close();
            gis.close();
            fis.close();

            if(filecount % 100 == 0){
                System.out.println();
            }else{
                System.out.print(".");
            }

        }
        bufw.flush();
        System.out.println("共处理:"+filecount+"个文件，共处理了"+datacount+"条数据！");

        bufw.close();
        fw.close();
    }
}
