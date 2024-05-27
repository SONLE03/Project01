package com.es.productService.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RollBackOrderEvent extends ApplicationEvent {
    private UUID orderId;

    public RollBackOrderEvent(Object source, UUID orderId) {
        super(source);
        this.orderId = orderId;
    }
}
