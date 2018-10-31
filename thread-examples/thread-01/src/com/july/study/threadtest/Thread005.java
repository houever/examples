package com.july.study.threadtest;

/**
 * 用户线程与守护线程
 * 用户线程会随着主线程的执行完成而结束
 *
 */
public class Thread005 {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(()->{
            for (int i = 0; i < 100; i++) {

                //线程休眠
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(" 我是子线程（用户线程） i:" + i);
            }
        });

        //将线程标识为守护线程
        thread.setDaemon(true);

        thread.start();

        for(int i=0;i<=5;i++){
            Thread.sleep(2000);
            System.out.println("主线程 "+i);
        }

    }

}
