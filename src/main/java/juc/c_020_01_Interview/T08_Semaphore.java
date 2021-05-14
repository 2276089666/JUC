package juc.c_020_01_Interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class T08_Semaphore {
    // 添加volatile，使t2能够得到通知
    volatile List lists = new ArrayList();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    static Thread t1 = null, t2 = null;

    public static void main(String[] args) {
        T08_Semaphore c = new T08_Semaphore();
        Semaphore s = new Semaphore(1);
        Semaphore s2 = new Semaphore(0);

        t1 = new Thread(() -> {
            try {
                s.acquire();
                for (int i = 0; i < 10; i++) {
                    c.add(new Object());
                    System.out.println("add " + i);
                    if (c.size()==5){
                        s2.release();
                        s.acquire();
                    }

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }, "t1");

        t2 = new Thread(() -> {
            try {
                s2.acquire();
                System.out.println("t2 结束");
                s.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
