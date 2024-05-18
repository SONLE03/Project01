package com.clothing.warrantyservice.service;

import com.clothing.warrantyservice.dto.request.WarrantyInvoiceRequest;
import com.clothing.warrantyservice.dto.response.OrderEventResponse;

public interface WarrantyService {

    void createWarrantyInvoice(WarrantyInvoiceRequest warrantyInvoiceRequest);

    void createWarranty(OrderEventResponse orderEventResponse);
}
