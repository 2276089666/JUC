package juc.c_021_02_AQS;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @Author ws
 * @Date 2021/5/16 13:41
 * @Version 1.0
 */
public class MySync extends AbstractQueuedSynchronizer {
    @Override
    protected boolean tryAcquire(int arg) {
        if (compareAndSetState(0,1)) {
            setExclusiveOwnerThread(Thread.currentThread());
            return true;
        }
        return false;
    }

    @Override
    protected boolean tryRelease(int arg) {
        setExclusiveOwnerThread(null);
        int i = getState();
        if (compareAndSetState(i,0)) {
            return true;
        }
        return false;
    }
}
