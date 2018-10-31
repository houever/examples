package com.july.study.threadtest;

/**
 * 使用多线程模拟售票
 *
 * 此案例会导致两个线程同时竞争一个变量，部分火车票会重复
 *
 */

class TestDemo01 implements Runnable{

    //同时多个售票窗口共享100张票
    private int count = 100;//此变量存在于方法区中


    @Override
    public void run() {

        while (count > 0){

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //当电影票>0 时，执行卖票方法
            sale();
        }
    }

    public void sale() {

        if(count > 0){
            System.out.println(Thread.currentThread().getName()+"卖出第"+(100-count+1)+"张票");
            count --;
        }

    }
}


public class Test01 {


    public static void main(String[] args) {
        TestDemo01 testDemo01 = new TestDemo01();

        Thread t1 = new Thread(testDemo01,"1号线程");
        Thread t2 = new Thread(testDemo01,"2号线程");

        t1.start();
        t2.start();

    }
}

