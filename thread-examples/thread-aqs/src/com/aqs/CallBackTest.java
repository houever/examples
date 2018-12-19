package com.aqs;

import java.util.concurrent.*;

/***
 * Future 模式
 */
public class CallBackTest {

    public static void main(String[] args) {
        ExecutorService exec = Executors.newFixedThreadPool(10);

        Future<Long> future = exec.submit(new Callable<Long>() {
            @Override
            public Long call() throws Exception {

                //执行业务逻辑
                return 3L;
            }
        });

        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

}
