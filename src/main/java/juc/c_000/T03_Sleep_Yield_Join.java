package juc.c_000;

public class T03_Sleep_Yield_Join {
    public static void main(String[] args) {
//        testSleep();
        testYield();
//        testJoin();
    }

    public static void testSleep() {
        new Thread(()->{
            for(int i=0; i<100; i++) {
                System.out.println("A" + i);
                try {
                    Thread.sleep(500);   // sleep完回到就绪状态
                    //TimeUnit.Milliseconds.sleep(500)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void testYield() {
        new Thread(()->{
            for(int i=0; i<100; i++) {
                System.out.println("A" + i);
                if(i%10 == 0) Thread.yield(); // 让步本线程,然后就绪状态


            }
        }).start();

        new Thread(()->{
            for(int i=0; i<100; i++) {
                System.out.println("------------B" + i);
                if(i%10 == 0) Thread.yield();
            }
        }).start();
    }

     public static void testJoin() {
        Thread t1 = new Thread(()->{
            for(int i=0; i<100; i++) {
                System.out.println("t1:\t" + i);
                try {
                    Thread.sleep(500);
                    //TimeUnit.Milliseconds.sleep(500)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(()->{
            try {
                t1.join();  // 去运行t1的方法,完了再运行本线程
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(int i=0; i<100; i++) {
                System.out.println("t2:\t" + i);
                try {
                    Thread.sleep(500);
                    //TimeUnit.Milliseconds.sleep(500)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
    }
}
