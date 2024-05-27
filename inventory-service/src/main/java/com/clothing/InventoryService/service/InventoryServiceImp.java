package com.clothing.InventoryService.service;

import com.clothing.InventoryService.constant.APIStatus;
import com.clothing.InventoryService.dto.request.ImportInvoiceRequest;
import com.clothing.InventoryService.dto.response.ProductEventResponse;
import com.clothing.InventoryService.dto.response.ImportInvoiceResponse;
import com.clothing.InventoryService.dto.response.ProductResponse;
import com.clothing.InventoryService.event.InventoryImportEvent;
import com.clothing.InventoryService.exception.BusinessException;
import com.clothing.InventoryService.model.ImportInvoice;
import com.clothing.InventoryService.model.ImportItem;
import com.clothing.InventoryService.model.ImportItemId;
import com.clothing.InventoryService.repository.ImportInvoiceRepository;
import com.clothing.InventoryService.repository.ImportItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class InventoryServiceImp implements InventoryService{
    private final ProductService productService;
    private final ImportInvoiceRepository importInvoiceRepository;
    private final ImportItemRepository importItemRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final AuthService authService;
    @Override
    public List<ImportInvoiceResponse> getImports() {
        return null;
    }

    @Override
    public ImportInvoice getImportById(UUID importId) {
        return null;
    }

    @Override
    @Transactional
    public String createImportInvoice(List<ImportInvoiceRequest> importInvoiceRequests) {
        List<ProductResponse> productList = productService.getAllProducts();
        Set<UUID> productSet = new HashSet<>();
        for (ProductResponse product : productList) {
            productSet.add(product.getId());
        }
        ImportInvoice importInvoice = new ImportInvoice();
        importInvoice.setCreatedBy(authService.getIdLogin());
        importInvoice = importInvoiceRepository.save(importInvoice);
        BigDecimal total = BigDecimal.ZERO;
        List<ImportItem> importItems = new ArrayList<>();
        List<ProductEventResponse> importEventResponseList = new ArrayList<>();
        for (ImportInvoiceRequest importInvoiceRequest : importInvoiceRequests) {
            if (!productSet.contains(importInvoiceRequest.getProductId())) {
                throw new BusinessException(APIStatus.PRODUCT_NOT_FOUND);
            }
            total = total.add(importInvoiceRequest.getTotal());
            Integer quantity = importInvoiceRequest.getQuantity();
            ImportItemId importItemId = new ImportItemId();
            importItemId.setImportId(importInvoice.getId());
            importItemId.setProductItemId(importInvoiceRequest.getProductId());

            ImportItem importItem = new ImportItem();
            importItem.setId(importItemId);
            importItem.setImportInvoice(importInvoice);
            importItem.setQuantity(quantity);
            importItem.setPrice(importInvoiceRequest.getPrice());
            importItem.setTotal(importInvoiceRequest.getTotal());
            importItems.add(importItem);

            importEventResponseList.add(new ProductEventResponse(importInvoiceRequest.getProductId(), quantity));
            // update quantity in product
        }
        Date currentDate = new Date();
        Timestamp time = new Timestamp(currentDate.getTime());
        importInvoice.setTotal(total);
        importInvoice.setCreatedAt(time);
        importInvoice.setUpdatedAt(time);
        importItemRepository.saveAll(importItems);

        applicationEventPublisher.publishEvent(new InventoryImportEvent(this, importEventResponseList));
        return  "Import invoice was created successfully";
    }
}
