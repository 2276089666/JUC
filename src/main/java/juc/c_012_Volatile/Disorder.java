package juc.c_012_Volatile;
/**
 * cpu指令重排的小程序验证
 */

import java.util.concurrent.CountDownLatch;

/**
 * @Author ws
 * @Date 2021/5/26 18:45
 */
public class Disorder {
    private static int x=0,y=0;
    private static int a=0,b=0;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < Long.MAX_VALUE; i++) {
            x=0;
            y=0;
            a=0;
            b=0;
            CountDownLatch countDownLatch = new CountDownLatch(2);
            Thread t1 = new Thread(() -> {
                a = 1;
                x = b;
                countDownLatch.countDown();
            });
            Thread t2 = new Thread(() -> {
                b = 1;
                y = a;
                countDownLatch.countDown();
            });
            t1.start();
            t2.start();
            countDownLatch.await();
            String result="第"+i+"次"+"("+x+","+y+")";
            if (x==0&&y==0){
                System.out.println(result);
                break;
            }
        }
    }
}
