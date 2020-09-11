package cn.tedu.lambda;

public class Demo {
    public static void main(String[] args) {
        Job job = new Job();
        Thread t = new Thread(job);
        t.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("这是任务！");
            }
        }).start();

        new Thread(()-> System.out.println("lambda启动的线程")).start();
    }
}
class Job implements Runnable {
    @Override
    public void run() {
        System.out.println("这是一个线程执行的任务！");
    }
}
