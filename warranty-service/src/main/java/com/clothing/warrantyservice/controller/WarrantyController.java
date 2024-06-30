package com.clothing.warrantyservice.controller;

import com.clothing.warrantyservice.dto.request.WarrantyInvoiceRequest;
import com.clothing.warrantyservice.service.WarrantyService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/warranty-service/api")
@AllArgsConstructor
public class WarrantyController {
    private final WarrantyService warrantyService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public String createWarranty(@RequestBody @Valid WarrantyInvoiceRequest warrantyInvoiceRequest){
        warrantyService.createWarrantyInvoice(warrantyInvoiceRequest);
        return "Warranty was created successfully";
    }
}
