package juc.c_022_RefTypeAndThreadLocal;

import java.io.IOException;

public class T01_NormalReference {
    public static void main(String[] args) throws IOException {
        M m = new M();
        m = null;
        System.gc(); //DisableExplicitGC

        System.in.read();  // 阻塞当前线程,避免程序退出观察不到结果,gc是跑在别的线程上
    }
}
