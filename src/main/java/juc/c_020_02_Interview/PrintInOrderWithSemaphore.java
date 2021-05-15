package juc.c_020_02_Interview;


import java.util.concurrent.Semaphore;

/**
 * @Author ws
 * @Date 2021/5/15 9:17
 * @Version 1.0
 */
public class PrintInOrderWithSemaphore {
    // 使用信号量
    public static class FooBar {
        private int n = 0;

        Semaphore foo=new Semaphore(1);
        Semaphore bar=new Semaphore(0);

        public FooBar(int n) {
            this.n = n;
        }

        public void foo() {
            for (int i = 0; i < n; i++) {
                try {
                    foo.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print("foo");
                bar.release();
            }
        }

        public void bar()  {
            for (int i = 0; i < n; i++) {
                try {
                    bar.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print("bar");
                foo.release();
            }
        }
    }

    public static void main(String[] args) {
        FooBar fooBar = new FooBar(5);
        new Thread(fooBar::foo).start();
        new Thread(fooBar::bar).start();
    }
}
