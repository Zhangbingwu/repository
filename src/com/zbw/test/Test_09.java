package com.zbw.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可打断锁
 * -	阻塞状态：包括普通阻塞，等待队列，锁池队列。
 * 1.	普通阻塞：sleep(1000),可以被打断。
 * 	Thread.interrupt()打断线程阻塞状态，并抛出异常。
 * 2.	等待队列：wait()方法被调用，也是一种阻塞状态，只能由notify()唤醒。
 * 	无法被打断。
 * 3.	锁池队列：无法获取锁标记。
 * 	使用ReentrantLock的lockInterruptibly()方法时，可以被打断。
 * 	使用lock()时，不能被打断。
 */
public class Test_09 {
    private Lock lock = new ReentrantLock();
    void m1(){
        try {
            lock.lock();
            for(int i=0;i<5;i++){
                System.out.println("m1---"+i);
                TimeUnit.SECONDS.sleep(1);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            lock.unlock();
        }
    }
    void m2(){
        try {
            lock.lockInterruptibly();
            System.out.println("m2开始了！");
        } catch (InterruptedException e) {
            System.out.println("m2被打断阻塞了！");    //thread2被打断
        } finally {
            lock.unlock();  //
        }
    }

    public static void main(String[] args) {
        Test_09 t = new Test_09();
        new Thread(new Runnable() {
            @Override
            public void run() {
                t.m1();
            }
        }).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                t.m2();
            }
        });
        thread2.start();
        //等待2s后打断thread2线程的阻塞状态。
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread2.interrupt();
    }
}
