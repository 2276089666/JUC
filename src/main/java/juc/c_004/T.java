/**
 * synchronized关键字
 * 对某个对象加锁
 *
 */

package juc.c_004;

public class T {

	private static int count = 10;
	
	public synchronized static void m() { //这里等同于synchronized(FineCoarseLock.class)
		count--;
		System.out.println(Thread.currentThread().getName() + " count = " + count);
	}
	
	public static void mm() {
		// T的Class对象在同一个classLoader里面只有一个
		synchronized(T.class) { //考虑一下这里写synchronized(this)是否可以？不行this是运行时产出的对象,该方法是静态
			count --;
		}
	}

}
