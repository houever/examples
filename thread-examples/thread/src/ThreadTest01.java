/**
 * @program: thread-examples
 * @description:
 * @author: houqijun
 * @create: 2019-04-06 11:21
 **/
public class ThreadTest01 {

    public static void main(String[] args) throws InterruptedException {
        ThreadDemo t = new ThreadDemo();

        for (int i = 0; i <= 100; i++) {
            System.out.println("我是1的线程，线程id==>" + Thread.currentThread().getName());
        }
        t.join();


    }

    //新建一个类，继承Thread，重写run方法，即可创建一个新的线程
    static class ThreadDemo extends Thread {
        @Override
        public void run() {
            for (int i = 0; i <= 100; i++) {
                System.out.println("我是2的线程，线程id==>" + Thread.currentThread().getName());
            }
        }
    }
}
