package com.zbw.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁
 * 原本的OS底层CPU在线程调度时，采取竞争原则。
 * 公平在哪里？等待时间最长的一定能获得锁。
 * CPU增加对等待时间的计算。
 */
public class Test_10 {
    public static void main(String[] args) {
        TestFairLock t = new TestFairLock();
        Thread t1 = new Thread(t);
        Thread t2 = new Thread(t);
        Thread t3 = new Thread(t);
        t1.start();
        t2.start();
        t3.start();
    }

}

class TestFairLock extends Thread {
    private static Lock fairLock = new ReentrantLock(true);

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            try {
                fairLock.lock();
                System.out.println("---" + i + "---" + Thread.currentThread().getName()+"获得了锁");
                TimeUnit.MILLISECONDS.sleep(300);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                fairLock.unlock();
                System.out.println(Thread.currentThread().getName()+"释放了锁");
            }
        }
    }
}
