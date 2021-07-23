package juc.c_018_00_AtomicXXX;

/**
 * LongAdder采用分段锁,当线程数非常多的时候,它会把所有线程分组去做CAS操作,然后再合并结果
 * 三种操作都各有优点,需要合适的场景
 * 效率 LongAddr > Atomic > sync
 */

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class T02_AtomicVsSyncVsLongAdder {
    static long count2 = 0L;
    static AtomicLong count1 = new AtomicLong(0L);
    static LongAdder count3 = new LongAdder();

    public static void main(String[] args) throws Exception {
        Thread[] threads = new Thread[1000];

        // AtomicLong
        for(int i=0; i<threads.length; i++) {
            threads[i] =
                    new Thread(()-> {
                        for(int k=0; k<100000; k++) count1.incrementAndGet();
                    });
        }
        long start = System.currentTimeMillis();
        for(Thread t : threads ) t.start();
        for (Thread t : threads) t.join();
        long end = System.currentTimeMillis();
        System.out.println("Atomic: " + count1.get() + " time " + (end-start));


        // sync
        Object lock = new Object();
        for(int i=0; i<threads.length; i++) {
            threads[i] =
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        for (int k = 0; k < 100000; k++)
                            synchronized (lock) {
                                count2++;
                            }
                    }
                });
        }
        start = System.currentTimeMillis();
        for(Thread t : threads ) t.start();
        for (Thread t : threads) t.join();
        end = System.currentTimeMillis();
        System.out.println("Sync: " + count2 + " time " + (end-start));


        // LongAdder
        for(int i=0; i<threads.length; i++) {
            threads[i] =
                    new Thread(()-> {
                        for(int k=0; k<100000; k++) count3.increment();
                    });
        }
        start = System.currentTimeMillis();
        for(Thread t : threads ) t.start();
        for (Thread t : threads) t.join();
        end = System.currentTimeMillis();
        System.out.println("LongAdder: " + count1.longValue() + " time " + (end-start));

    }
}
