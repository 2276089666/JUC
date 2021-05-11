/**
 * 分析一下这个程序的输出
 *
 */

package juc.c_005;

public class T implements Runnable {

	private volatile int count = 100;
	
	public /*synchronized*/ void run() { 
		count--;
		System.out.println(Thread.currentThread().getName() + " count = " + count);
	}
	
	public static void main(String[] args) {
		T t = new T();
		for(int i=0; i<100; i++) {
			new Thread(t, "THREAD:\t" + i).start();
		}
		// 导致线程读到的不是最新值,上面两个关键字加一个就行,都保证了可见性
	}
	
}
