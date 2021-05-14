package juc.c_020;

/**
 * 读写锁的升级版
 * 官方例子
 * 下面是一个点移动的一个抽象。首先对于move()操作，加锁和解锁的过程和普通的Lock类似。
 * 对于distanceFromOrigin()，首先会使用tryOptimisticRead()加一个乐观读锁，此时会返回一个stamp数值。
 * 获取到X和Y的值之后，去验证其有效性，如果尚未被修改过，那么读取成功，计算结果返回。如果验证失败，那么会加读锁。
 *
 * 在这个例子中，使用的锁升级，里面调用了readLock()方法去操作。这个操作会有一个循环的CAS操作，直到获取到锁为止。
 */

import java.util.concurrent.locks.StampedLock;

/**
 * @Author ws
 * @Date 2021/5/14 18:54
 * @Version 1.0
 */
public class T14_TestStampedLock {
    private double x, y;
    private final StampedLock sl = new StampedLock();

     void move(double deltaX, double deltaY) {
        long stamp = sl.tryWriteLock();
         System.out.println(Thread.currentThread().getName()+"\twrite:\t版本号"+stamp);
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            sl.unlockWrite(stamp);
        }
    }

    double distanceFromOrigin() {
        long stamp = sl.tryOptimisticRead();
        double currentX = x, currentY = y;
        System.out.println(Thread.currentThread().getName()+"\tread:\t版本号"+stamp);
        if (!sl.validate(stamp)) {
            stamp = sl.readLock();
            try {
                currentX = x;
                currentY = y;
            } finally {
                sl.unlockRead(stamp);
            }
        }
        double sqrt = Math.sqrt(currentX * currentX + currentY * currentY);
        System.out.println("结果:\t"+sqrt);
        return sqrt;
    }

    public static void main(String[] args) {
        T14_TestStampedLock t = new T14_TestStampedLock();

        for (int i = 0; i <10 ; i++) {
            new Thread(t::distanceFromOrigin,"Thread"+i).start();
        }
        for (int i = 0; i < 2; i++) {
            new Thread(()->{
               t.move(3,4);
            },"write"+i).start();
        }
    }

}
