import java.util.concurrent.*;

/**
 * @program: thread-examples
 * @description:
 * @author: houqijun
 * @create: 2019-04-06 11:21
 **/
public class ThreadTest03 {

    public static void main(String[] args) throws Exception {

        //匿名内部类方式创建Callable接口
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "Hello";
            }
        };
        //使用FutureTask模式创建线程
        FutureTask<String> future = new FutureTask<String>(callable);

        //新建线程，并启动
        new Thread(future).start();

        //阻塞获取线程返回值
        String s = future.get();
        System.out.println(s);
    }
}
