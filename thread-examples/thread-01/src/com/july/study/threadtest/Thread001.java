package com.july.study.threadtest;

class Demo01 extends Thread{

    @Override
    public void run() {
//        System.out.println(this.getId());//线程ID
//        System.out.println(this.getName());//线程名称
//        System.out.println(this.getPriority());//线程优先级
//        System.out.println(this.getState());//线程状态

        for(int i=0;i<=100;i++){
            System.out.println("子线程开始 id="+this.getId()+"     name=="+this.getName());
        }

    }
}

public class Thread001 {

    public static void main(String[] args) {
        Thread thread = new Thread(new Demo01());
        thread.start();
        for(int i=0;i<=100;i++){
            System.out.println("主线程开始 id="+ Thread.currentThread().getId()+"     name=="+Thread.currentThread().getName());
        }
    }

}
