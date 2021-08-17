package juc.ABA;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Author ws
 * @Date 2021/8/17 13:43
 */
public class AtomicStampedReferenceDemo {
    /**
     * 为了解决CAS的ABA问题,我们打算加时间戳来解决,AtomicStampedReference刚好就是这样实现的
     */

    // money的初始值是10,时间戳的初始值是0
    static AtomicStampedReference<Integer> money=new AtomicStampedReference<>(10,0);

    public static void main(String[] args) throws InterruptedException {
        int stamp = money.getStamp();

        // A->B
        new Thread(()->{
            Integer moneyReference = money.getReference();
            money.compareAndSet(moneyReference,moneyReference-5,stamp,stamp+1);
        }).start();

        Thread.sleep(1000);
        System.out.println(money.getReference());

        // B->A
        new Thread(()->{
            int stamp2 = money.getStamp();
            Integer moneyReference = money.getReference();
            money.compareAndSet(moneyReference,moneyReference+5,stamp2,stamp2+1);
        }).start();

        Thread.sleep(1000);
        System.out.println(money.getReference());

        // 修改失败,stamp对不上
        new Thread(()->{
            Integer moneyReference = money.getReference();
            money.compareAndSet(moneyReference,moneyReference+100,stamp,stamp+1);
        }).start();

        Thread.sleep(1000);
        System.out.println(money.getReference());
    }
}
