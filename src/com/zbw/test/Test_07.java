package com.zbw.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁
 * concurrent包下
 */
public class Test_07 {
    Lock lock = new ReentrantLock();
    void m1(){
        try {
            lock.lock();//加锁
            for(int i=0;i<10;i++){
                System.out.println("第"+i+"次！");
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();//解锁
        }
    }
    void m2(){
        lock.lock();
        System.out.println("m2----");
        lock.unlock();
    }
    public static void main(String[] args) {
        Test_07 t = new Test_07();
        new Thread(new Runnable() {
            @Override
            public void run() {
                t.m1();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                t.m2();
            }
        }).start();
    }
}
