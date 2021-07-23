package juc.c_020_02_Interview;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author ws
 * @Date 2021/5/15 9:40
 * @Version 1.0
 */
public class PrintInOrderWithReentrantLockAndCondition {
    // 使用可重入锁和Condition精确唤醒线程
    public static class FooBar {
        private int n = 0;

        ReentrantLock lock=new ReentrantLock();
        Condition condition=lock.newCondition();
        volatile boolean sign=false;  // 标志位

        public FooBar(int n) {
            this.n = n;
        }

        public void foo() {
            for (int i = 0; i < n; i++) {
                try {
                    lock.lock();
                    while (sign){  // 等待释放锁
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print("foo");
                    sign=true;
                    condition.signal();
                }finally {
                    lock.unlock();
                }

            }
        }

        public void bar()  {
            for (int i = 0; i < n; i++) {
                try {
                    lock.lock();
                    while (!sign){  // 等待释放锁
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print("bar");
                    sign=false;
                    condition.signal();
                }finally {
                    lock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) {
       FooBar fooBar = new FooBar(5);
        new Thread(fooBar::foo).start();
        new Thread(fooBar::bar).start();
    }
}
