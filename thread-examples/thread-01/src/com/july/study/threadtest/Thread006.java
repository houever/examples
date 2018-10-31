package com.july.study.threadtest;

/**
 * 用户线程与守护线程
 * 用户线程会随着主线程的执行完成而结束
 *
 */
public class Thread006 {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(()->{
            for(int i=0;i<=10;i++){
                System.out.println("子线程i===>"+i);
            }
        });

        thread.start();

        //当前线程放弃执行权限，等待thread线程执行结束，才执行
        thread.join();
        for(int i=0;i<=5;i++){
            Thread.sleep(2000);
            System.out.println("主线程 "+i);
        }

    }

}
