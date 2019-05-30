/**
 * @program: thread-examples
 * @description:
 * @author: houqijun
 * @create: 2019-04-06 11:21
 **/
public class ThreadTest02 {

    public static void main(String[] args) {

        System.out.println("我是主线程，线程id==>"+Thread.currentThread().getName());

        //实现Runnable接口方式
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("我是子线程，线程id==>"+Thread.currentThread().getName());
            }
        }).start();

        System.out.println("我是主线程，线程id==>"+Thread.currentThread().getName());

    }
}
