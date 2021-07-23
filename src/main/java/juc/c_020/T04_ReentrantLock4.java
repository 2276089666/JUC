/**
 * 
 * 使用ReentrantLock还可以调用lockInterruptibly方法，可以对线程interrupt方法做出响应，
 * 在一个线程等待锁的过程中，可以被打断
 * 
 * 可以用来解决某些可能出现死锁的代码上
 *
 */
package juc.c_020;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class T04_ReentrantLock4 {
		
	public static void main(String[] args) {
		Lock lock = new ReentrantLock();
		
		
		Thread t1 = new Thread(()->{
			try {
				lock.lock();
				System.out.println("t1 start");
				TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
				System.out.println("t1 end");
			} catch (InterruptedException e) {
				System.out.println("interrupted!");
			} finally {
				lock.unlock();
			}
		});
		t1.start();
		
		Thread t2 = new Thread(()->{
			boolean status=false;
			try {
				//lock.lock();
				lock.lockInterruptibly(); //支持对interrupt()方法做出响应
				System.out.println("t2 start");
				status=true;
			} catch (InterruptedException e) {
				System.out.println("interrupted!");
				try {
					// 业务的继续执行
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException interruptedException) {
					interruptedException.printStackTrace();
				}
				System.out.println("t2 end");
			} finally {
				if (status)
				lock.unlock();
			}
		});
		t2.start();
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t2.interrupt(); //线程2拿不到锁会等待,但是上面的加锁方式支持被打断,也就是叫线程2别去拿锁了,抛出异常,我们抓住异常从而做出其他操作
		
	}
}
