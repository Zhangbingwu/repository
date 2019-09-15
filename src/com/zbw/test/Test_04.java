package com.zbw.test;

import java.util.concurrent.TimeUnit;

public class Test_04 {
    int a = 0;
    int m1(){
        try {
            /**
             * 这里相当于有一个临时变量_returnValue
             * int _returnValue = a;
             * return _returnValue; //0
             */
            return a;
        }finally {
            /**
             * 这里修改了a,不影响_returnValue
             */
            a = 10;
        }
    }

    Object o = new Object();
    void m2(){
        synchronized (o){   //这个o是一个临时的和Object o 不同
            while(true){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"o:"+o);
            }
        }
    }
    public static void main(String[] args) {
        Test_04 t = new Test_04();
        System.out.println(t.m1());
        System.out.println(t.a);
        new Thread(new Runnable() {
            @Override
            public void run() {
                t.m2();
            }
        },"Thread1").start();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                t.m2();
            }
        },"Thread2");
        //修改o
        t.o = new Object();
        thread.start();
    }
}
