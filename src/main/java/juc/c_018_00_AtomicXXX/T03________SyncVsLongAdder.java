package juc.c_018_00_AtomicXXX;

/**
 *  大部分情况下LongAdder比sync还是效率要高
 */


import java.util.concurrent.atomic.LongAdder;

public class T03________SyncVsLongAdder {
    static long count2 = 0L;
    static LongAdder count = new LongAdder();

    public static void main(String[] args) throws Exception {
        Thread[] threads = new Thread[500];

        for(int i=0; i<threads.length; i++) {
            threads[i] =
                    new Thread(()-> {
                        for(int k=0; k<100000; k++) count.increment();
                    });
        }
        long start = System.currentTimeMillis();
        for(Thread t : threads ) t.start();
        for (Thread t : threads) t.join();
        long end = System.currentTimeMillis();
        System.out.println("LongAdder: " + count.longValue() + " time " + (end-start));



        Object lock = new Object();
        for(int i=0; i<threads.length; i++) {
            threads[i] =
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int k=0; k<100000; k++) {
                            synchronized (lock) {
                                count2++;
                            }
                        }
                    }
                });
        }
        start = System.currentTimeMillis();
        for(Thread t : threads ) t.start();
        for (Thread t : threads) t.join();
        end = System.currentTimeMillis();
        System.out.println("Sync: " + count2 + " time " + (end-start));

    }

}
