package com.clothing.InventoryService.controller;

import com.clothing.InventoryService.dto.request.ImportInvoiceRequest;
import com.clothing.InventoryService.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/inventory-service/api")
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public String createImport(@RequestBody @Valid List<ImportInvoiceRequest> request){
        inventoryService.createImportInvoice(request);
        return "Import invoice was created successfully";
    }
}
