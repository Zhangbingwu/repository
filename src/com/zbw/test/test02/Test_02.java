package com.zbw.test.test02;

import java.util.concurrent.TimeUnit;

/**
 * 使用volatile 线程间可见
 */
public class Test_02 {
    private Container container = new Container();
    private final Object lock = new Object();

    void sys() {
        while(true){
            if (container.size() == 5) {
                System.out.println("-------输出信息!");
                break;
            }
        }
    }

    void m1() {
        for (int i = 0; i < 10; i++) {
            container.add(new Object());
            System.out.println(container.size());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Test_02 t = new Test_02();
        new Thread(new Runnable() {
            @Override
            public void run() {
                t.sys();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                t.m1();
            }
        }).start();
    }
}

