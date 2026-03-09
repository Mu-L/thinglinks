package com.mqttsnet.thinglinks.product.event;

import com.mqttsnet.thinglinks.product.event.source.ProductInfoUpdatedEventSource;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Description:
 * 产品信息更新事件
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/1/19
 */
@Getter
public class ProductInfoUpdatedEvent extends ApplicationEvent {
    private final ProductInfoUpdatedEventSource eventSource;

    public ProductInfoUpdatedEvent(ProductInfoUpdatedEventSource source) {
        super(source);
        this.eventSource = source;
    }
}
