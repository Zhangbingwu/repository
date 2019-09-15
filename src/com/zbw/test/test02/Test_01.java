package com.zbw.test.test02;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 使用CountDownLatch
 */
public class Test_01 {
    private CountDownLatch latch = new CountDownLatch(1);
    private Container container = new Container();
    private final Object lock = new Object();

    void sys() {
        if (container.size() != 5) {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("-------输出信息!");
    }

    void m1() {
        synchronized (lock) {

            for (int i = 0; i < 10; i++) {
                container.add(new Object());
                if (container.size() == 5) {
                    latch.countDown();
                }
                System.out.println(container.size());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Test_01 t = new Test_01();
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

class Container {
    private volatile List<Object> container = new ArrayList<>();

    public void add(Object o) {
        container.add(o);
    }

    public int size() {
        return container.size();
    }
}
