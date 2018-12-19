package com.july.study.dis.event;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

public class OrderEventProducer {

    private RingBuffer<OrderEvent> ringBuffer;


    public OrderEventProducer(RingBuffer<OrderEvent> ringBuffer) {

        this.ringBuffer = ringBuffer;
    }

    public void sendData(ByteBuffer byteBuffer){
        //1在生产者发送消息的时候，首先从我们需要的ringBuffer里面获取一个可用的序号
        long sequence = ringBuffer.next();
        try {
            //2根据这个序号找到具体的"OrderEvent"元素,注意：此时获取的OrderEvent对象是一个没有被赋值的空对象（属性未被赋值）
            OrderEvent event = ringBuffer.get(sequence);
            //3进行实际的赋值处理
            event.setValue(byteBuffer.getLong(0));
        }finally {
            //4提交发布操作
            ringBuffer.publish(sequence);
        }


    }

}
