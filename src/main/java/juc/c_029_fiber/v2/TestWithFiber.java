package juc.c_029_fiber.v2;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.SuspendableRunnable;

import java.util.concurrent.ExecutionException;

/**
 * @Author ws
 * @Date 2021/6/3 16:31
 */

/**
 * 与v1的实验结果对比可以发现纤程速度更快
 *  纤程:用户态的线程,比线程粒度还要小的线程,我们普通的java线程最终会由JVM向操作系统协调由OS创建出对应的线程,切换调度
 *  都归OS,但是纤程不必,属于用户态的线程,内核没有纤程的TCB(线程控制块),无法调度纤程
 *  OS创建一个线程需要内存: 1M   纤程:4K
 *  可见纤程切换简单,创建数量可以很多
 */
public class TestWithFiber {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int size = 100000;
        Fiber[] fibers = new Fiber[size];
        long start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            fibers[i] = new Fiber<Void>(new SuspendableRunnable() {
                @Override
                public void run() throws SuspendExecution, InterruptedException {
                    calc();
                }
            });
        }

        for (int i = 0; i < size; i++) {
            fibers[i].start();
        }

        for (int i = 0; i < size; i++) {
            fibers[i].join();
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
