package com.july.study.threadtest;

/**
 * 1、如何解决多线程之间的安全问题？
 * 使用线程同步关键字 synchronized或使用lock锁
 * 2、为什么使用关键字或者锁能解决线程安全问题？
 * 让当前线程执行完成之后再释放锁让其他线程执行，保证数据的原子性，解决线程的不安全问题
 * 3、什么是多线程之间的同步？
 *    当多个线程同时共享一个资源，不会受到其他线程的干扰
 *
 *  4、synchronized使用方法？
 *      修饰方法，同步方法
 *                      1、修饰非静态方法
 *                      2、修饰静态方法
 *      修饰代码块
 *                      1、synchronized(类.class){}
 *                      2、synchronized(this){}
 *
 * 使用时建议使用同步代码块，只包裹可能会发生线程安全问题的代码
 *
 */
class TestDemo04 implements Runnable{

    //同时多个售票窗口共享100张票
    private static int count = 100;//此变量存在于方法区中

    private static Object oj = new Object();

    public static boolean flag = true;


    @Override
    public void run() {
        if(flag){
            while (count > 0){
                synchronized (this.getClass()){
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println(Thread.currentThread().getName()+"卖出第"+(100-count+1)+"张票");
                count --;
            }
        }else{
            while (count>0){
                sale();
            }
        }
    }

    //使用synchronized同步对象
    public void sale() {
        synchronized (oj){
            if(count > 0){
                System.out.println(Thread.currentThread().getName()+"卖出第"+(100-count+1)+"张票");
                count --;
            }
        }
    }
}


public class Test04 {


    public static void main(String[] args) throws InterruptedException {
        TestDemo03 testDemo01 = new TestDemo03();
        TestDemo03 testDemo02 = new TestDemo03();

        Thread t1 = new Thread(testDemo01,"1号线程");
        Thread t2 = new Thread(testDemo02,"2号线程");

        t1.start();
        Thread.sleep(40);
        TestDemo04.flag = false;
        t2.start();

    }
}

