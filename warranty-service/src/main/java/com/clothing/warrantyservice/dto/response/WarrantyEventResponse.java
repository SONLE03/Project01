package com.clothing.warrantyservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;

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
