package com.clothing.warrantyservice.dto;

import com.clothing.warrantyservice.dto.response.OrderEventResponse;
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

