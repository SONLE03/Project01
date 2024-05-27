package com.clothing.OrderService.event;

import com.clothing.OrderService.dto.SagaDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class SagaEvent extends ApplicationEvent {
    private SagaDTO sagaDTO;

    public SagaEvent(Object source, SagaDTO sagaDTO) {
        super(source);
        this.sagaDTO = sagaDTO;
    }
}
