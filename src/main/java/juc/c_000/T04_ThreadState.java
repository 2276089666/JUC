package juc.c_000;


import java.util.concurrent.locks.LockSupport;


public class T04_ThreadState {

   static final Object lock=new Object();

    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println(this.getState());  // runnable
            LockSupport.park();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock){
                System.out.println("========to do=========");
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            synchronized (lock){
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        Thread t = new MyThread();
        System.out.println(t.getState());  // new
        t.start();

        Thread.sleep(1000);
        System.out.println(t.getState());  // waiting(park)

        LockSupport.unpark(t);
        Thread.sleep(100);
        System.out.println(t.getState());  // time_waiting(sleep)

        Thread.sleep(6000);
        System.out.println(t.getState());  // blocking(synchronized,注意lock等都是waiting只有synchronized才是blocking)

        try {
            t.join();  // 等t运行完,查看状态,TERMINATED
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(t.getState());

    }
}
