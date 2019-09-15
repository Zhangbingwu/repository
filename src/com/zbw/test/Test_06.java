package com.zbw.test;

import java.util.concurrent.CountDownLatch;

public class Test_06 {
    CountDownLatch latch = new CountDownLatch(3);   //上三个门闩
    void m1(){
        try {
            latch.await();  //等待门闩全部解开
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m1()门闩全部消失");
    }
    void m2(){
        while (latch.getCount()>=0) {
            if (latch.getCount() == 0) {
                m1();
            }
            latch.countDown();
            System.out.println("当前门闩：" + latch.getCount());
        }
    }

    public static void main(String[] args) {
        Test_06 t = new Test_06();
        new Thread(new Runnable() {
            @Override
            public void run() {
                t.m2();
            }
        }).start();
    }
}
