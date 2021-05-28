package juc.c_028_FalseSharing;

import java.util.concurrent.CountDownLatch;

/**
 * @Author ws
 * @Date 2021/5/26 15:29
 */
public class Contended {
    private static class T {
        // 要关闭对这个注解限制 -XX:-RestrictContended
        // 只有JDK1.8起作用
        @sun.misc.Contended     // 保证这个变量不与别的属性同行，而且解决cpu的缓存行可能不为64字节，导致程序失效
        public volatile long x = 0L;
    }

    public static T[] arr = new T[2];

    static {
        arr[0] = new T();
        arr[1] = new T();
    }

    public static void main(String[] args) throws Exception {

        CountDownLatch countDownLatch = new CountDownLatch(2);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1_0000_0000L; i++) {
                arr[0].x = i;
            }
            countDownLatch.countDown();
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1_0000_0000L; i++) {
                arr[1].x = i;
            }
            countDownLatch.countDown();
        });
        long start = System.nanoTime();   // 取当前系统的纳秒级别时间
        t1.start();
        t2.start();
        countDownLatch.await();
        long end = System.nanoTime() - start;
        System.out.println("费时" + end /10_0000L);
    }
}
