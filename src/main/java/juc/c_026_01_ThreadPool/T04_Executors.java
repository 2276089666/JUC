/**
 * Executors提供创建各种线程池的方法,和创建线程工厂
 */
package juc.c_026_01_ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class T04_Executors {
	public static void main(String[] args) {
		//Executors
		ThreadFactory threadFactory = Executors.defaultThreadFactory();
		ExecutorService executorService = Executors.newCachedThreadPool();
	}
}
