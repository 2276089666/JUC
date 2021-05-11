package juc.c_000;

public class T04_ThreadState {

    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println(this.getState());  // RUNNABLE

            for(int i=0; i<10; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(i);
            }
        }
    }

    public static void main(String[] args) {
        Thread t = new MyThread();

        System.out.println(t.getState());  // NEW

        t.start();

        try {
            t.join();  // 等t运行完,查看状态,TERMINATED
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(t.getState());

    }
}
