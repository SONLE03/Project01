package com.clothing.OrderService.dto;

import com.clothing.OrderService.dto.response.event.OrderEventResponse;
import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SagaDTO {
    private OrderEventResponse orderEventResponse;
}
