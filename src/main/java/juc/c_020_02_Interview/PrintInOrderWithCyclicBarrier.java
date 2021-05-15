package juc.c_020_02_Interview;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author ws
 * @Date 2021/5/15 9:26
 * @Version 1.0
 */
public class PrintInOrderWithCyclicBarrier {
    // 使用栅栏
    public static class FooBar {
        private int n = 0;

        CyclicBarrier cyclicBarrier=new CyclicBarrier(2);

        public FooBar(int n) {
            this.n = n;
        }

        public void foo() {
            for (int i = 0; i < n; i++) {
                System.out.print("foo");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }

        public void bar()  {
            for (int i = 0; i < n; i++) {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.print("bar");

            }
        }
    }

    public static void main(String[] args) {
        FooBar fooBar = new FooBar(5);
        new Thread(fooBar::foo).start();
        new Thread(fooBar::bar).start();
    }
}
