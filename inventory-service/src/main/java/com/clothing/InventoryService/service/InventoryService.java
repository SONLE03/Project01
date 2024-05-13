package com.clothing.InventoryService.service;

import com.clothing.InventoryService.dto.request.ImportInvoiceRequest;
import com.clothing.InventoryService.dto.response.ImportInvoiceResponse;
import com.clothing.InventoryService.model.ImportInvoice;

import java.util.List;
import java.util.UUID;

public interface InventoryService {
    List<ImportInvoiceResponse> getImports();

    ImportInvoice getImportById(UUID importId);
    String createImportInvoice(List<ImportInvoiceRequest> importInvoiceRequests);
}
