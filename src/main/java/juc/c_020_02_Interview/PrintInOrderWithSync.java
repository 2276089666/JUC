package juc.c_020_02_Interview;


/**
 * @Author ws
 * @Date 2021/5/15 10:32
 * @Version 1.0
 */
public class PrintInOrderWithSync {
    // 使用synchronized+标志位+唤醒
    public static class FooBar {
        private int n = 0;

        final Object o=new Object();
        volatile boolean sign=true;

        public FooBar(int n) {
            this.n = n;
        }

        public void foo() {
            for (int i = 0; i < n; i++) {
                synchronized (o){
                    // 等待释放锁
                    while (!sign){
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print("foo");
                    sign=false;
                    o.notify();
                }
            }
        }

        public void bar()  {
            for (int i = 0; i < n; i++) {
                synchronized (o){
                    // 等待释放锁
                    while (sign){
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print("bar");
                    sign=true;
                    o.notify();
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
