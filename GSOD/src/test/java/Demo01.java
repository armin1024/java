public class Demo01 {


    public static void main(String[] args) {
        String str = "A51256 00451 BRANSON WEST MUNICIPAL EMERSO US   MO KFWB  +36.699 -093.402 +0411.2 20140731 20200624";

        //通过字符的位置来获取
        String s = str.substring(0, 6); //substring()方法包前不包后，所以要读取到下标6的位置
        String wban = str.substring(7, 12);
        String ctry = str.substring(43, 47);
        String lat = str.substring(57, 64);
        String lon = str.substring(65, 73);

        System.out.println(s);
        System.out.println(wban);
        System.out.println(ctry);
        System.out.println(lat);
        System.out.println(lon);
    }
}
