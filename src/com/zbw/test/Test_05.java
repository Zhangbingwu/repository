package com.zbw.test;

public class Test_05 {
    /**
     * 在定义同步代码块时，不要使用常量对象作为锁对象。
     * 锁的是同一个对象 s1 s2两个不同的reference
     */
    String s1 = "hello";
    String s2 = "hello";
    Integer i1 = 1;         //这是在常量池中的变量 (0~128缓存)
    Integer i2 = new Integer(1); //只要new 就会在堆内存创建新对象。

    void m1(){
        synchronized (i1){
            System.out.println("m1");
            while (true){}
        }
    }
    void m2(){
        synchronized (i2){
            System.out.println("m2");
            while (true){}
        }
    }

    public static void main(String[] args) {
        Test_05 t = new Test_05();
        new Thread(new Runnable() {
            @Override
            public void run() {
                t.m1();
                t.m2();
            }
        }).start();
    }
}
