package juc.c_029_fiber.v3;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.SuspendableRunnable;

import java.util.concurrent.ExecutionException;

/**
 * @Author ws
 * @Date 2021/6/3 17:16
 */

/**
 * 我们弄10个线程出来,每个线程再创建一定的纤程,再由每个纤程执行计算,让线程和纤程同时并发,程序速度更快
 */
public class TestWithFiber2 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int size = 100000;
        long start = System.currentTimeMillis();
        Thread[] threads = new Thread[10];

        for (int i = 0; i < 10; i++) {

            threads[i]=new Thread(()->{
                Fiber[] fibers = new Fiber[size/10];
                for (int j = 0; j < size/10; j++) {
                    fibers[j] = new Fiber<Void>(new SuspendableRunnable() {
                        @Override
                        public void run() throws SuspendExecution, InterruptedException {
                            calc();
                        }
                    });
                }
                for (int j = 0; j < size / 10; j++) {
                    fibers[j].start();
                }

            });
        }

        for (int i = 0; i < 10; i++) {
            threads[i].start();
        }

        for (int i = 0; i < 10; i++) {
            threads[i].join();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    static void calc() {
        int result = 0;
        for (int i = 0; i < 10000; i++) {
            for (int j = 0; j < 2000; j++) {
                result += j;
            }
        }
    }
}
