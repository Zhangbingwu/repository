package com.zbw.test;

import java.util.concurrent.TimeUnit;

public class Test_02 {
    volatile boolean b = true;
    public void m1(){
        System.out.println("m1 start");
        while(b){
        }
        System.out.println("m1 end");
    }

    public static void main(String[] args) {
        Test_02 t = new Test_02();
        new Thread(new Runnable() {
            @Override
            public void run() {

                t.m1();
            }
        }).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        }catch (Exception e){
            e.printStackTrace();
        }
        t.b = false;
    }
}
