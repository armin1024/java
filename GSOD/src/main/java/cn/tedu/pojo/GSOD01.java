package cn.tedu.pojo;

public class GSOD01 {
    private String usaf = "usaf";
    private String wban = "wban";
    private String moda = "moda";//月，日
    private String temp = "temp";//平均气温
    private String visib = "visib";//平均可见度
    private String wdsp = "wdsp";//平均风速

    public String getUsaf() {
        return usaf;
    }

    public void setUsaf(String usaf) {
        this.usaf = usaf;
    }

    public String getWban() {
        return wban;
    }

    public void setWban(String wban) {
        this.wban = wban;
    }

    public String getModa() {
        return moda;
    }

    public void setModa(String moda) {
        this.moda = moda;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getVisib() {
        return visib;
    }

    public void setVisib(String visib) {
        this.visib = visib;
    }

    public String getWdsp() {
        return wdsp;
    }

    public void setWdsp(String wdsp) {
        this.wdsp = wdsp;
    }

    @Override
    public String toString() {
        return "GSOD{" +
                "usaf='" + usaf + '\'' +
                ", wban='" + wban + '\'' +
                ", moda='" + moda + '\'' +
                ", temp='" + temp + '\'' +
                ", visib='" + visib + '\'' +
                ", wdsp='" + wdsp + '\'' +
                '}';
    }
}
