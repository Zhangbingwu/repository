package com.zbw.test;

import java.util.LinkedList;

/**
 * 多线程完成生产者消费者模式
 * wait/notify  配置while循环使用
 * if判断只能判断一次，可能出现虚假唤醒。if判断完成后，执行到wait会立刻执行其他的线程，完成后从wait之后开始执行，不会再次判断。
 * while循环可以多次判断，必须出现。
 */
public class Test_11 {
    public static void main(String[] args) {
        TestContainer t = new TestContainer();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<20;i++){
                    t.put(new Object());
                    System.out.println("生产------"+i);
                }
            }
        }).start();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    t.get();
                    System.out.println(Thread.currentThread().getName()+"消费---"+t.getCount());
                }
            }
        });
        t1.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    t.get();
                    System.out.println(Thread.currentThread().getName()+"消费---"+t.getCount());
                }
            }
        }).start();
    }
}
class TestContainer<E>{
    LinkedList<E> list = new LinkedList<>();
    private int count = 0;
    private final int MAX = 10;
    public synchronized int getCount(){
        return count;
    }
    public synchronized void put(E e){
        while (list.size()==MAX){
            try {
                this.wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        list.add(e);
        count++;
        this.notifyAll();
    }
    public synchronized E get(){
        while (list.size()==0){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        count--;
        E e = list.removeFirst();
        this.notifyAll();
        return e;
    }
}
