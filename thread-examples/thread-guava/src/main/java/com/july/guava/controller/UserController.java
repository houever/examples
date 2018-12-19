package com.july.guava.controller;

import com.google.common.util.concurrent.*;
import com.july.guava.model.Order;
import com.july.guava.model.Result;
import com.july.guava.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Callable;

@RestController
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private ListeningExecutorService listeningExecutorService;

    @GetMapping(value = "createOrder")
    public Result createOrder(Order order){
        long start = System.currentTimeMillis();
        ListenableFuture<Long> userServiceFuture = listeningExecutorService.submit(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return userService.userService();
            }
        });
        ListenableFuture<Long> itemServiceFuture = listeningExecutorService.submit(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return userService.itemService();
            }
        });
        ListenableFuture<Long> stockServiceFuture = listeningExecutorService.submit(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return userService.stockService();
            }
        });

        final ListenableFuture<List<Long>> successfulAsList = Futures.successfulAsList(userServiceFuture,itemServiceFuture,itemServiceFuture);

        //对于多个listenablefuture进行转换，返回一个新的listenablefuture对象
        final ListenableFuture<Long> transform = Futures.transformAsync(successfulAsList, new AsyncFunction<List<Long>, Long>() {

            /**
             *
             * @param input
             * @return
             * @throws Exception
             */
            @Override
            public ListenableFuture<Long> apply(@Nullable List<Long> input) throws Exception {

                if(input ==null || input.isEmpty()){
                    return null;
                }
                //这里可以对input进行复杂的处理，返回最终的一个结果
                //比如：对用户服务，订单服务，商品服务的远程调用结果进行封装
                long result = 0;

                for(Long serviceResult : input){
                    if(serviceResult != null){
                        result += serviceResult;
                    }
                }

                //立即返回一个带返回值的listablefuture
                return Futures.immediateFuture(result);
            }
        }, listeningExecutorService);

        //继续调用第四个服务
        ListenableFuture<Long> addressServiceFuture = listeningExecutorService.submit(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return userService.addressService();
            }
        });

        //把结果加上第四个结果
        final ListenableFuture<List<Long>> finalFutures = Futures.successfulAsList(transform,addressServiceFuture);

        ListenableFuture<Long> finalResult = Futures.transformAsync(finalFutures, new AsyncFunction<List<Long>, Long>() {
            /**
             *
             * @param longs
             * @return
             * @throws Exception
             */
            @Override
            public ListenableFuture<Long> apply(@Nullable List<Long> longs) throws Exception {
                if(longs ==null || longs.isEmpty()){
                    return null;
                }
                long res = 0;
                for(Long serviceResult : longs){
                    if(serviceResult != null){
                        res += serviceResult;
                    }
                }
                return Futures.immediateFuture(res);
            }
        }, listeningExecutorService);

        addCallBack(finalResult);
        return Result.success();

    }

    private void addCallBack(ListenableFuture<Long> finalResult) {

        Futures.addCallback(finalResult, new FutureCallback<Long>() {
            @Override
            public void onSuccess(@Nullable Long aLong) {
                System.out.println("服务回调成功，执行结果是"+aLong);
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("服务调用异常");
            }
        },listeningExecutorService);

    }

}
