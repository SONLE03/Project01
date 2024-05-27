package com.clothing.OrderService.dto.response.event;

import com.clothing.OrderService.dto.response.CustomerResponse;
import com.clothing.OrderService.dto.response.OrderItemResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WarrantyEventResponse {
    private CustomerResponse customerResponse;
    private HashMap<UUID, Pair<List<UUID>, Integer>> productItemListToCreateWarranty;
}
