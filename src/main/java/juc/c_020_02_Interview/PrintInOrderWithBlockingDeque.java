package juc.c_020_02_Interview;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Author ws
 * @Date 2021/5/15 8:58
 * @Version 1.0
 */
public class PrintInOrderWithBlockingDeque {
    /**
     * 我们提供一个类：
     * <p>
     * class FooBar {
     * public void foo() {
     *     for (int i = 0; i < n; i++) {
     *       print("foo");
     *     }
     * }
     * <p>
     * public void bar() {
     *     for (int i = 0; i < n; i++) {
     *       print("bar");
     *     }
     * }
     * }
     * 两个不同的线程将会共用一个 FooBar 实例。其中一个线程将会调用 foo() 方法，另一个线程将会调用 bar() 方法。
     * <p>
     * 请设计修改程序，以确保 "foobar" 被输出 n 次。
     * <p>
     * 示例 1:
     * <p>
     * 输入: n = 1
     * 输出: "foobar"
     * 解释: 这里有两个线程被异步启动。其中一个调用 foo() 方法, 另一个调用 bar() 方法，"foobar" 将被输出一次。
     * 示例 2:
     * <p>
     * 输入: n = 2
     * 输出: "foobarfoobar"
     * 解释: "foobar" 将被输出两次。
     */

    // 使用阻塞队列
    public static class FooBar {
        private int n = 0;

        private BlockingDeque<Integer> foo=new LinkedBlockingDeque(1);
        private BlockingDeque<Integer> bar=new LinkedBlockingDeque(1);

        public FooBar(int n) {
            this.n = n;
        }

        public void foo() {
            for (int i = 0; i < n; i++) {
                try {
                    foo.put(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print("foo");
                try {
                    bar.put(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void bar()  {
            for (int i = 0; i < n; i++) {
                try {
                    bar.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print("bar");
                try {
                    foo.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
