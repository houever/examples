package com.july.study.dis.event;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {

        //参数准备工作
        OrderEventFactory factory = new OrderEventFactory();
        //ringBufferSize
        int ringBufferSize = 1024 * 1024;

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        //1实例化disruptor对象
        Disruptor<OrderEvent> disruptor = new Disruptor<OrderEvent>(
                factory,
                ringBufferSize,
                executorService,
                ProducerType.SINGLE,
                new BlockingWaitStrategy());

        //2添加消费者的监听(构建disruptor 与消费者的关联关系)
        EventHandlerGroup<OrderEvent> orderEventEventHandlerGroup = disruptor.handleEventsWith(new OrderEventHandler());

        //3启动disrutpro方法
        disruptor.start();

        //4 获取 实际存储数据的容器 ringbuffer
        RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();

        OrderEventProducer orderEventProducer = new OrderEventProducer(ringBuffer);

        //初始化一个字节码
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);

        for(int i=1;i<=100;i++){
            byteBuffer.putLong(0,i);
            orderEventProducer.sendData(byteBuffer);
        }

        disruptor.shutdown();

        executorService.shutdown();






    }
}
