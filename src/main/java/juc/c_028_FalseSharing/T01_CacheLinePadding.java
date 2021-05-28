package juc.c_028_FalseSharing;


import java.util.concurrent.CountDownLatch;

public class T01_CacheLinePadding {
    private static class T {
//        private Long p1, p2, p3, p4, p5, p6, p7;  // 56字节
        public volatile long x = 0L;
//        private Long p9, p10, p11, p12, p13, p14, p15;   // 56字节
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
        final long start = System.nanoTime();   // 取当前系统的纳秒级别时间
        t1.start();
        t2.start();
        countDownLatch.await();
        long end = System.nanoTime() - start;
        System.out.println("费时" + end / 10_0000L);
    }
}
