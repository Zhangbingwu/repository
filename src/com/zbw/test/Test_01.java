package com.zbw.test;

public class Test_01 {
    public synchronized void m1(){
        System.out.println("m1 start");
        try {
            Thread.sleep(3000);
            System.out.println("m1 end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void m2(){
        System.out.println(" m2-start");
        try {
            Thread.sleep(1500);
            System.out.println("m2 - end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
class CeShi implements Runnable{
    int i;
    Test_01 t;
    public CeShi(int i,Test_01 t){
        this.i = i;
        this.t = t;
    }
    @Override
    public void run() {
        if(i==0){
            t.m1();
        }else{
            t.m2();
        }
    }

    public static void main(String[] args) {
        Test_01 t = new Test_01();
        new Thread(new CeShi(0,t)).start();
        new Thread(new CeShi(1,t)).start();
    }
}