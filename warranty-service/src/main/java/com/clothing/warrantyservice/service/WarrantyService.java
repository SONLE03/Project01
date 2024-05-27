package com.clothing.warrantyservice.service;

import com.clothing.warrantyservice.dto.SagaDTO;
import com.clothing.warrantyservice.dto.request.WarrantyInvoiceRequest;
import com.clothing.warrantyservice.dto.response.CustomerResponse;
import com.clothing.warrantyservice.dto.response.OrderEventResponse;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface WarrantyService {

    void createWarrantyInvoice(WarrantyInvoiceRequest warrantyInvoiceRequest);

    void createWarranty(SagaDTO sagaDTO);
}
