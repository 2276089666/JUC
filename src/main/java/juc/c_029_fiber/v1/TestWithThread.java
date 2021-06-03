package juc.c_029_fiber.v1;


/**
 * @Author ws
 * @Date 2021/6/3 16:22
 */
public class TestWithThread {

    static void calc() {
        int result = 0;
        for (int i = 0; i < 10000; i++) {
            for (int j = 0; j < 2000; j++) {
                result += j;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int size = 100000;
        Thread[] threads = new Thread[size];
        long start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            threads[i] = new Thread(() -> {
                calc();
            });
        }

        for (int i = 0; i < size; i++) {
            threads[i].start();
        }

        for (int i = 0; i < size; i++) {
            threads[i].join();
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }
}
