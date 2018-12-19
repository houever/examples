package com.july.study.dis.event;

import com.lmax.disruptor.EventFactory;

/**
 * 创建事件工厂
 */
public class OrderEventFactory implements EventFactory<OrderEvent> {
    @Override
    public OrderEvent newInstance() {
        return new OrderEvent();
    }
}
