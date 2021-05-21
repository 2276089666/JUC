package juc.c_026_01_ThreadPool;

import java.util.concurrent.*;

public class T14_MyRejectedHandler {
    public static void main(String[] args) {
        ExecutorService service = new ThreadPoolExecutor(4, 4,
                0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(6),
                Executors.defaultThreadFactory(),
                new MyHandler());
    }

    static class MyHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            //log("r rejected") 记日志
            //save r kafka mysql redis   未执行的任务持久化
            //try 3 times   尝试三次
            if(executor.getQueue().size() < 10000) {
                //try put again();
            }
        }
    }
}
