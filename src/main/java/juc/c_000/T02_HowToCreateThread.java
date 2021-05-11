package juc.c_000;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class T02_HowToCreateThread {
    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("Hello MyThread!");
        }
    }

    static class MyRun implements Runnable {
        @Override
        public void run() {
            System.out.println("Hello MyRun!");
        }
    }

    static class MyCallable implements Callable{

        @Override
        public Object call() throws Exception {
            System.out.println("hello MyCallable");
            return null;
        }
    }

    public static void main(String[] args) {
        new MyThread().start();
        new Thread(new MyRun()).start();
        new Thread(()->{
            System.out.println("Hello Lambda!");
        }).start();

        FutureTask futureTask = new FutureTask<>(new MyCallable());
        futureTask.run();
    }

}

//请你告诉我启动线程的四种方式 1：Thread 2: Runnable 3:Executors.newCachedThrad 4.实现callable
