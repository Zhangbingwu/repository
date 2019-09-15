package com.zbw.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Test_03 {
    AtomicInteger count = new AtomicInteger(0);

    void m1(){
        for(int i=0;i<10000;i++){
            count.incrementAndGet();
        }
    }

    public static void main(String[] args) {
        Test_03 t = new Test_03();
        List<Thread> threads = new ArrayList<>();
        for(int i=0;i<10;i++){
            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    t.m1();
                }
            }));
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();//调用者阻塞，等待该线程执行完成后再运行。
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(t.count.intValue());
    }
}
