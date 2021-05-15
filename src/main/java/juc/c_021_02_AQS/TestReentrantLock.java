package juc.c_021_02_AQS;

import java.util.concurrent.locks.ReentrantLock;

public class TestReentrantLock {

    private static volatile int i = 0;

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock(true);
//        new Thread(()->{
//            lock.lock();
//        },"myThread").start();
        lock.lock();
        lock.unlock();
        //synchronized 程序员的丽春院 JUC
    }

    // ReentrantLock的加锁过程,源码跟踪(非公平)
    // 1.利用多态调用虚基类sync定义的接口,但是子类NonfairSync实现的方法lock
    /**
     *          final void lock() {
     *                  // unsafe的CAS操作,将AQS的volatile成员变量state改为1(可重入锁加上锁就赋值1,countDownLatch看他的初始化个数,state就赋值几)
     *             if (compareAndSetState(0, 1))
     *                  // 将当前线程赋给AQS的父类AOS成员变量exclusiveOwnerThread
     *                 setExclusiveOwnerThread(Thread.currentThread());
     *             else
     *                 // 如果CAS失败,就调用ReentrantLock重写的tryAcquire,并把这个线程扔到AQS的双向队列去排队,并中断当前线程
     *                 acquire(1);  // 公平锁直接调用这个,不用做上面的CAS尝试操作
     *         }
     */

    // 解锁
    // 调用AQS的release的tryRelease方法:1.将AQS的父类AOS成员变量exclusiveOwnerThread线程变为null
    //                    2.做CAS把我们的state变为0
    //
    // 调用AQS的release的unparkSuccessor将其他需要抢占锁的线程,CAS失败而中断的线程唤醒
}
