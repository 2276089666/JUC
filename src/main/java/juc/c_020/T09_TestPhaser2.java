package juc.c_020;

/**
 * CyclicBarrier的升级版
 *  多个栅栏,能在某个栅栏拦住某些线程和放走某些线程继续干后面的事
 */


import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class T09_TestPhaser2 {
    // 给睡眠随机数
    static Random r = new Random();

    static MarriagePhaser phaser = new MarriagePhaser();


    // 避免sleep函数的try catch的繁杂,在此封装
    static void milliSleep(int milli) {
        try {
            TimeUnit.MILLISECONDS.sleep(milli*10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        // @param parties the number of additional parties required to
        // advance to the next phase
        // 进入下一阶段所需的线程参与数量,和CyclicBarrier一样
        phaser.bulkRegister(7);

        for(int i=0; i<5; i++) {

            new Thread(new Person("p" + i)).start();
        }

        new Thread(new Person("新郎")).start();
        new Thread(new Person("新娘")).start();

    }



    static class MarriagePhaser extends Phaser {
        /**
         *
         * @param phase  移位编号,从0开始,进入一次加1
         * @param registeredParties  线程的数量
         * @return  一旦返回true,这个移位器就终止了,所以非终端操作返回false
         */
        @Override
        protected boolean onAdvance(int phase, int registeredParties) {

            switch (phase) {
                case 0:
                    System.out.println("所有人到齐了！" + registeredParties);
                    System.out.println();
                    return false;
                case 1:
                    System.out.println("所有人吃完了！" + registeredParties);
                    System.out.println();
                    return false;
                case 2:
                    System.out.println("所有人离开了！" + registeredParties);
                    System.out.println();
                    return false;
                case 3:
                    System.out.println("婚礼结束！新郎新娘抱抱！" + registeredParties);
                    return true;
                default:
                    return true;
            }
        }
    }


    static class Person implements Runnable {
        String name;

        public Person(String name) {
            this.name = name;
        }

        public void arrive() {

            milliSleep(r.nextInt(1000));
            System.out.printf("%s 到达现场！\n", name);
            phaser.arriveAndAwaitAdvance();  // 阻塞等待
        }

        public void eat() {
            milliSleep(r.nextInt(1000));
            System.out.printf("%s 吃完!\n", name);
            phaser.arriveAndAwaitAdvance();
        }

        public void leave() {
            milliSleep(r.nextInt(1000));
            System.out.printf("%s 离开！\n", name);


            phaser.arriveAndAwaitAdvance();
        }

        private void hug() {
            if(name.equals("新郎") || name.equals("新娘")) {
                milliSleep(r.nextInt(1000));
                System.out.printf("%s 洞房！\n", name);
                phaser.arriveAndAwaitAdvance();
            } else {
                phaser.arriveAndDeregister();  // 注销掉一个线程(7-1)
                //phaser.register()  // 增加一个线程(7+1)
            }
        }

        @Override
        public void run() {
            arrive();


            eat();


            leave();


            hug();

        }
    }
}


