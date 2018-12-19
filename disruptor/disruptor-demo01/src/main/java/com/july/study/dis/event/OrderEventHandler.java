package com.july.study.dis.event;


import com.lmax.disruptor.EventHandler;

/**
 * 消费者
 */
public class OrderEventHandler implements EventHandler<OrderEvent> {

    /**
     *
     * @param orderEvent    具体数据
     * @param l
     * @param b
     * @throws Exception
     */
    @Override
    public void onEvent(OrderEvent orderEvent, long l, boolean b) throws Exception {

        System.out.println("消费者消费数据"+orderEvent.getValue());

    }
}
