package com.zbw.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 尝试锁
 * ReentrantLock
 */
public class Test_08 {
    Lock lock = new ReentrantLock();
    void m1(){
        try {
            lock.lock();
            for (int i=0;i<10;i++){
                System.out.println(i+"---");
                TimeUnit.SECONDS.sleep(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    /**
     * 尝试获取锁，如果没有获取到，返回false
     * 如果获取到，返回true
     */
    void m2(){
        boolean isLocked = false;
        try {
            //不阻塞尝试锁，没有获取到就继续，不会等待获取。
//            isLocked = lock.tryLock();
            //阻塞尝试锁，时间参数为阻塞时间，会尝试等待获取5s，没有获取到就解除阻塞。
            isLocked = lock.tryLock(5, TimeUnit.SECONDS);
            System.out.println(isLocked);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if(isLocked){
                //如果获取到释放
                lock.unlock();
            }
        }
    }
    public static void main(String[] args) {
        Test_08 t = new Test_08();
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
